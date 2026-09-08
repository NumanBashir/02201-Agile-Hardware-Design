import chisel3._

class UseMux2 extends Module {
  val io = IO(new Bundle {
    val sel = Input(UInt(1.W))
    val dout = Output(UInt(1.W))
  })

  val a = 1.U
  val b = 0.U
  val sel = io.sel
  val res = Wire(UInt())

  // ***** your code starts here *****

  val mux = Module(new Mux2())
  mux.io.a := a
  mux.io.b := b
  mux.io.sel := sel
  res := mux.io.y

  // ***** your code ends here *****

  io.dout := res
}
