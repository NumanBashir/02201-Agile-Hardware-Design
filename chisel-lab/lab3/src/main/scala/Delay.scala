import chisel3._

class Delay extends Module {
  val io = IO(new Bundle {
    val din = Input(UInt(8.W))
    val dout = Output(UInt(8.W))
  })

  val res = Wire(UInt())

  // ***** your code starts here *****

  val firstReg = RegInit(0.U(8.W))
  val secondReg = RegInit(0.U(8.W))
  firstReg := io.din
  secondReg := firstReg
  res := secondReg

  // ***** your code ends here *****

  io.dout := res
}
