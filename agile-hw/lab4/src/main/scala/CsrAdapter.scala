import help._
import chisel3._

class ApbPort extends Bundle {
  val psel = Input(Bool())
  val penable = Input(Bool())
  val pwrite = Input(Bool())
  val paddr = Input(UInt(32.W))
  val pwdata = Input(UInt(32.W))
  val prdata = Output(UInt(32.W))
  val pready = Output(Bool())
  val pslverr = Output(Bool())
}

case class CsrField(path: Seq[String], address: BigInt, kind: String,
                    high: Int, low: Int, init: Option[BigInt]) {
  val width = high - low + 1
  val readable = kind != "wotrg"
  val writable = kind == "rw" || kind == "wotrg"
}

object CsrDescription {
  def number(s: String): BigInt =
    if (s.trim.startsWith("0x")) BigInt(s.trim.drop(2), 16)
    else BigDecimal(s.trim).toBigIntExact.getOrElse(
      throw new IllegalArgumentException(s"Not an integer: $s"))

  def load(file: String): Seq[CsrField] = {
    val sheets = Sheet.load(file)
    def records(s: Sheet) = s.rows.map(row => s.header.zip(row).toMap)
    val fields = records(sheets("Map")).flatMap { block =>
      require(block("Interface") == "APB", "Only APB is supported")
      val base = number(block("Base Address"))
      val end = number(block("End Address"))
      records(sheets(block("Block"))).map { row =>
        val address = base + number(row("Offset"))
        require(address >= base && address + 3 <= end && address % 4 == 0 &&
          address >= 0 && address < (BigInt(1) << 32), "Invalid register address")
        val range = row("Range").split(":").map(_.toInt)
        require(range.length == 2 && range(1) >= 0 && range(0) >= range(1) && range(0) < 32,
          "Invalid field range")
        val kind = row("Type")
        require(Set("rw", "ro", "wotrg", "rotrg", "const")(kind), "Unknown field type")
        val init = if (row("Init") == "?") None else Some(number(row("Init")))
        require(kind != "const" || init.nonEmpty, "Constants need a value")
        require(init.forall(v => v >= 0 && v < (BigInt(1) << (range(0) - range(1) + 1))),
          "Initial value does not fit")
        CsrField(Seq(block("Name"), row("Register")) ++
          Option(row("Field")).filter(_.nonEmpty).toSeq,
          address, kind, range(0), range(1), init)
      }
    }
    require(fields.nonEmpty, "No registers defined")
    require(fields.map(_.path).distinct.size == fields.size, "Duplicate field names")
    for (group <- fields.groupBy(_.address).values; pair <- group.combinations(2)) {
      val a = pair.head; val b = pair.last
      val overlaps = a.low <= b.high && b.low <= a.high
      require(!overlaps || !(a.readable && b.readable || a.writable && b.writable),
        "Overlapping fields in the same access direction")
    }
    fields
  }
}

class CsrAdapter(descriptionSheetPath: String) extends Module {
  val fields = CsrDescription.load(descriptionSheetPath)
  val apb = IO(new ApbPort)

  def fieldType(f: CsrField): Data = f.kind match {
    case "rw" => Output(UInt(f.width.W))
    case "ro" => Input(UInt(f.width.W))
    case "wotrg" | "rotrg" => new DynamicBundle(Seq(
      "data" -> (if (f.writable) Output(UInt(f.width.W)) else Input(UInt(f.width.W))),
      "trg" -> Output(Bool())))
  }
  def tree(items: Seq[(Seq[String], Data)]): DynamicBundle = {
    val names = items.map(_._1.head).distinct
    new DynamicBundle(names.map { name =>
      val children = items.filter(_._1.head == name)
      val leaf = children.filter(_._1.size == 1)
      require(leaf.isEmpty || children.size == 1, "Conflicting register and field names")
      name -> (if (leaf.nonEmpty) leaf.head._2 else tree(children.map {
        case (path, data) => (path.tail, data)
      }))
    })
  }
  val csr = IO(tree(fields.filter(_.kind != "const").map(f => f.path -> fieldType(f))))
  def port(path: Seq[String]): Data = path.foldLeft(csr: Data) {
    case (bundle, name) => bundle.asInstanceOf[DynamicBundle](name)
  }

  // Zero-wait-state APB slave: side effects only on an active access cycle.
  val access = apb.psel && apb.penable && !reset.asBool
  apb.pready := access
  val readAllowed = WireDefault(false.B)
  val writeAllowed = WireDefault(false.B)
  val readParts = fields.map { f =>
    val hit = apb.paddr === f.address.U(32.W)
    val write = access && apb.pwrite && hit
    if (f.readable) when(hit) { readAllowed := true.B }
    if (f.writable) when(hit) { writeAllowed := true.B }
    val value: UInt = f.kind match {
      case "const" => f.init.get.U(f.width.W)
      case "ro" => port(f.path).asUInt
      case "rotrg" =>
        port(f.path :+ "trg").asInstanceOf[Bool] := access && !apb.pwrite && hit
        port(f.path :+ "data").asUInt
      case "rw" | "wotrg" =>
        val reg = f.init match {
          case Some(v) => RegInit(v.U(f.width.W))
          case None => Reg(UInt(f.width.W))
        }
        when(write) { reg := apb.pwdata(f.high, f.low) }
        if (f.kind == "rw") port(f.path).asInstanceOf[UInt] := reg
        else {
          port(f.path :+ "data").asInstanceOf[UInt] := reg
          port(f.path :+ "trg").asInstanceOf[Bool] := write
        }
        reg
    }
    if (f.readable) Mux(hit, (value.pad(32) << f.low)(31, 0), 0.U(32.W))
    else 0.U(32.W)
  }
  apb.prdata := Mux(access && !apb.pwrite, readParts.reduce(_ | _), 0.U)
  apb.pslverr := access && Mux(apb.pwrite, !writeAllowed, !readAllowed)
}

object CsrAdapter extends App {
  _root_.circt.stage.ChiselStage.emitSystemVerilogFile(
    new CsrAdapter(args.headOption.getOrElse("soc.xlsx")),
    Array("--target-dir", "generated"))
}
