import chisel3._

/**
  * Use Mux2 components to build a 4:1 multiplexer
  */

class Mux4 extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val c = Input(UInt(1.W))
    val d = Input(UInt(1.W))
    val sel = Input(UInt(2.W))
    val y = Output(UInt(1.W))
  })

  // ***** your code starts here *****

  val muxAB = Module(new Mux2())
  val muxCD = Module(new Mux2())
  val muxOut = Module(new Mux2())

  // The low select bit chooses within each pair.
  muxAB.io.a := io.a
  muxAB.io.b := io.b
  muxAB.io.sel := io.sel(0)
  muxCD.io.a := io.c
  muxCD.io.b := io.d
  muxCD.io.sel := io.sel(0)

  // The high select bit chooses the pair.
  muxOut.io.a := muxAB.io.y
  muxOut.io.b := muxCD.io.y
  muxOut.io.sel := io.sel(1)
  io.y := muxOut.io.y

  // ***** your code ends here *****
}
