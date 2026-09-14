import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MuxTestPayload extends Bundle {
  val data = UInt(8.W)
  val tag = UInt(3.W)
  val valid = Bool()
}

class GenericMux4Test extends AnyFlatSpec with ChiselScalatestTester {
  behavior of "GenericMux4"

  for (width <- Seq(1, 8, 16)) {
    it should s"select every input for $width-bit UInt values" in {
      test(new GenericMux4(UInt(width.W))) { dut =>
        val random = new scala.util.Random(42)
        val limit = (BigInt(1) << width) - 1
        val cases = Seq(Seq.fill(4)(BigInt(0)), Seq.fill(4)(limit)) ++
          (0 until 4).map(i => Seq.tabulate(4)(j => if (i == j) limit else BigInt(0))) ++
          Seq.fill(100)(Seq.fill(4)(BigInt(width, random)))
        cases.foreach { values =>
          values.zipWithIndex.foreach { case (value, i) => dut.io.in(i).poke(value.U) }
          for (sel <- 0 until 4) {
            dut.io.sel.poke(sel.U)
            dut.io.out.expect(values(sel).U)
          }
        }
      }
    }
  }

  it should "preserve negative and positive SInt values" in {
    test(new GenericMux4(SInt(8.W))) { dut =>
      for (values <- Seq(Seq(-128, -1, 0, 127), Seq(12, -42, 100, -99))) {
        values.zipWithIndex.foreach { case (value, i) => dut.io.in(i).poke(value.S) }
        for (sel <- 0 until 4) {
          dut.io.sel.poke(sel.U)
          dut.io.out.expect(values(sel).S)
        }
      }
    }
  }

  it should "select Bool values for every input pattern" in {
    test(new GenericMux4(Bool())) { dut =>
      for (pattern <- 0 until 16) {
        for (i <- 0 until 4) dut.io.in(i).poke(((pattern & (1 << i)) != 0).B)
        for (sel <- 0 until 4) {
          dut.io.sel.poke(sel.U)
          dut.io.out.expect(((pattern & (1 << sel)) != 0).B)
        }
      }
    }
  }

  it should "select all fields of a Bundle together" in {
    test(new GenericMux4(new MuxTestPayload)) { dut =>
      // Keep sel fixed while inputs change as well as changing sel afterwards.
      for (round <- 0 until 2) {
        for (i <- 0 until 4) {
          dut.io.in(i).data.poke((round * 100 + i * 11).U)
          dut.io.in(i).tag.poke((7 - i - round).U)
          dut.io.in(i).valid.poke(((i + round) % 2 == 0).B)
        }
        for (sel <- Seq(3, 0, 2, 1)) {
          dut.io.sel.poke(sel.U)
          dut.io.out.data.expect((round * 100 + sel * 11).U)
          dut.io.out.tag.expect((7 - sel - round).U)
          dut.io.out.valid.expect(((sel + round) % 2 == 0).B)
        }
      }
    }
  }
}
