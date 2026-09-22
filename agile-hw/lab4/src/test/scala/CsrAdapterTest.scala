import chisel3._
import chiseltest._
import help.DynamicBundle
import org.scalatest.flatspec.AnyFlatSpec

class CsrAdapterTest extends AnyFlatSpec with ChiselScalatestTester {
  def signal(d: CsrAdapter, path: String): Data = path.split('.').foldLeft(d.csr: Data) {
    case (b, n) => b.asInstanceOf[DynamicBundle](n)
  }
  def bus(d: CsrAdapter) = new ApbMasterBfm(d.clock, d.reset, d.apb.psel,
    d.apb.penable, d.apb.paddr, d.apb.pwrite, d.apb.pwdata,
    d.apb.prdata, d.apb.pready, d.apb.pslverr)

  "CSR adapter" should "reset, mask writes, and keep GPIO banks independent" in {
    test(new CsrAdapter("soc.xlsx")) { d =>
      val b = bus(d); b.reset()
      b.readExpect(0x41003000L, Some(0))
      assert(b.write(0x41003000L, 0xffffffffL).nonEmpty)
      b.readExpect(0x41003000L, Some(3))
      signal(d, "uart0.ctrl.en").asInstanceOf[UInt].expect(1.U)
      val rng = new scala.util.Random(42)
      for (_ <- 0 until 25) {
        val a = BigInt(32, rng); val c = BigInt(32, rng)
        assert(b.write(0x41004004L, a).nonEmpty)
        assert(b.write(0x41004014L, c).nonEmpty)
        b.readExpect(0x41004004L, Some(a))
        b.readExpect(0x41004014L, Some(c))
      }
      b.reset()
      for (addr <- Seq(0x41003000L, 0x41004000L, 0x41004004L, 0x4100400cL,
        0x41004010L, 0x41004014L, 0x4100401cL)) b.readExpect(addr, Some(0))
    }
  }

  it should "pack hardware inputs and reject invalid or forbidden accesses" in {
    test(new CsrAdapter("soc.xlsx")) { d =>
      val b = bus(d); b.reset()
      signal(d, "uart0.status.txEmpty").asInstanceOf[UInt].poke(1.U)
      signal(d, "uart0.status.rxReady").asInstanceOf[UInt].poke(1.U)
      b.readExpect(0x41003004L, Some(3))
      signal(d, "gpio0.dataIn").asInstanceOf[UInt].poke("h87654321".U)
      b.readExpect(0x41004008L, Some(BigInt("87654321", 16)))
      b.readExpect(0x80000000L, Some(BigInt("deadbeef", 16)))
      for (addr <- Seq(0x41003004L, 0x41004008L, 0x80000000L, 0x50000000L, 0x41003001L))
        assert(b.write(addr, 123).isEmpty)
      for (addr <- Seq(0x50000000L, 0x41003001L, 0x4100300cL)) b.readExpect(addr, None)
      b.readExpect(0x80000000L, Some(BigInt("deadbeef", 16)))
    }
  }

  it should "gate writes and triggers with APB access and preserve trigger data" in {
    test(new CsrAdapter("soc.xlsx")) { d =>
      val b = bus(d); b.reset()
      val tx = signal(d, "uart0.data.txData.trg").asInstanceOf[Bool]
      val rx = signal(d, "uart0.data.rxData.trg").asInstanceOf[Bool]
      val data = signal(d, "uart0.data.txData.data").asInstanceOf[UInt]
      d.apb.paddr.poke("h41003008".U)
      d.apb.pwdata.poke("h12345a".U)
      d.apb.pwrite.poke(true.B)
      d.apb.psel.poke(true.B); d.apb.penable.poke(false.B)
      d.clock.step(3); tx.expect(false.B); rx.expect(false.B)
      d.apb.penable.poke(true.B)
      d.apb.pready.expect(true.B); tx.expect(true.B); rx.expect(false.B)
      d.clock.step()
      d.apb.psel.poke(false.B); d.apb.penable.poke(false.B)
      tx.expect(false.B); data.expect(0x5a.U)
      d.apb.pwdata.poke(0.U); d.clock.step(2); data.expect(0x5a.U)
      signal(d, "uart0.data.rxData.data").asInstanceOf[UInt].poke(0xaa.U)
      d.apb.psel.poke(true.B); d.apb.pwrite.poke(false.B)
      d.clock.step(); rx.expect(false.B)
      d.apb.penable.poke(true.B)
      rx.expect(true.B); tx.expect(false.B); d.apb.prdata.expect(0xaa.U)
      d.clock.step()
      d.apb.psel.poke(false.B); d.apb.penable.poke(false.B)
      rx.expect(false.B); d.apb.pslverr.expect(false.B)
      // An aborted setup must not write a register.
      d.apb.paddr.poke("h41003000".U); d.apb.pwrite.poke(true.B)
      d.apb.pwdata.poke(3.U); d.apb.psel.poke(true.B)
      d.clock.step(2); d.apb.psel.poke(false.B)
      b.readExpect(0x41003000L, Some(0))
    }
  }
  it should "generate a different register map from a second spreadsheet" in {
    test(new CsrAdapter("src/test/resources/tiny.xlsx")) { d =>
      val b = bus(d); b.reset()
      signal(d, "unit.mixed.status").asInstanceOf[UInt].poke(0x35.U)
      b.readExpect(0x1000, Some(0x35a0))
      assert(b.write(0x1000, 0xffff).nonEmpty)
      b.readExpect(0x1000, Some(0x35f0))
      b.readExpect(0x1004, None)
      assert(b.write(0x1004, 0x123).nonEmpty)
      signal(d, "unit.kick.data").asInstanceOf[UInt].expect(3.U)
      b.readExpect(0x1008, Some(0x42))
      assert(b.write(0x1008, 0).isEmpty)
      b.readExpect(0x41003000L, None)
      b.reset(); b.readExpect(0x1000, Some(0x35a0))
    }
  }
}
