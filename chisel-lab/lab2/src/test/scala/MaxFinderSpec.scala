import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class MaxFinderSpec extends AnyFlatSpec with ChiselScalatestTester {
  "MaxFinder " should "pass" in {
    test(new MaxFinder) { dut =>
      for (a <- 0 to 15) {
        for (b <- 0 to 15) {
          for (c <- 0 to 15) {
            for (d <- 0 to 15) {
              dut.io.a.poke(a.U)
              dut.io.b.poke(b.U)
              dut.io.c.poke(c.U)
              dut.io.d.poke(d.U)
              dut.clock.step(1)
              val res = List(a, b, c, d).max
              val idx = List(a, b, c, d).indexOf(res)
              dut.io.max.expect(res.U, s"Max of $a, $b, $c, $d should be $res")
            }
          }
        }
      }
    }
  }
}
