import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MaxFinderTest extends AnyFlatSpec with ChiselScalatestTester with Matchers {
  "MaxFinder" should "find the maximum value in a Vec" in {
    test(new MaxFinder(4, 8)) { dut =>
      val cases = Seq(
        Seq(0, 0, 0, 0),
        Seq(42, 42, 42, 42),
        Seq(255, 255, 255, 255),
        Seq(1, 2, 3, 4),
        Seq(4, 3, 2, 1),
        Seq(127, 128, 0, 1),
        Seq(9, 200, 200, 3)
      ) ++ (0 until 4).map(i => Seq.tabulate(4)(j => if (i == j) 255 else j))

      cases.foreach { values =>
        values.zipWithIndex.foreach { case (value, i) => dut.io.in(i).poke(value.U) }
        dut.io.max.expect(values.max.U)
      }
    }
  }

  for ((n, width) <- Seq((1, 1), (2, 4), (3, 8), (5, 12), (8, 16))) {
    it should s"match a software maximum for $n inputs of $width bits" in {
      test(new MaxFinder(n, width)) { dut =>
        val random = new scala.util.Random(42)
        for (_ <- 0 until 100) {
          val values = Seq.fill(n)(BigInt(width, random))
          values.zipWithIndex.foreach { case (value, i) => dut.io.in(i).poke(value.U) }
          dut.io.max.expect(values.max.U)
        }
      }
    }
  }
}
