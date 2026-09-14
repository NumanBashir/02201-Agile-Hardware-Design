
import chisel3._
import chisel3.util._

/**
  * Find the maximum value in a Vec using reduceTree
  * @param n number of elements
  */
class MaxFinder(val n: Int, val width: Int) extends Module {
  require(n > 0, "MaxFinder needs at least one input")
  require(width > 0, "MaxFinder needs a positive bit width")

  val io = IO(new Bundle {
    val in = Input(Vec(n, UInt(width.W)))
    val max = Output(UInt(width.W))
  })

  // Each tree node compares two unsigned values and forwards the larger.
  io.max := io.in.reduceTree((a, b) => Mux(a > b, a, b))
}

object MaxFinderVerilog extends App {
  _root_.circt.stage.ChiselStage.emitSystemVerilogFile(
    new MaxFinder(4, 8),
    Array("--target-dir", "generated")
  )
}
