import chisel3._

/** Select one of four values of the same Chisel data type. */
class GenericMux4[T <: Data](gen: T) extends Module {
  val io = IO(new Bundle {
    val in = Input(Vec(4, gen))
    val sel = Input(UInt(2.W))
    val out = Output(gen)
  })

  // Dynamic Vec indexing generates combinational selection hardware.
  io.out := io.in(io.sel)
}

object GenericMux4Verilog extends App {
  _root_.circt.stage.ChiselStage.emitSystemVerilogFile(
    new GenericMux4(UInt(8.W)),
    Array("--target-dir", "generated")
  )
}
