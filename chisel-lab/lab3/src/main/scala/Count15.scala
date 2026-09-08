import chisel3._

class Count15 extends Module {
  val io = IO(new Bundle {
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val cntReg = RegInit(0.U(4.W))
  // Four-bit arithmetic wraps from 15 back to 0.
  cntReg := cntReg + 1.U
  res := cntReg

  // ***** your code ends here *****

  io.dout := res
}
