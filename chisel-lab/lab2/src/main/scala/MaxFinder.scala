import chisel3._

class MaxFinder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(4.W))
    val b = Input(UInt(4.W))
    val c = Input(UInt(4.W))
    val d = Input(UInt(4.W))
    val max = Output(UInt(4.W))
  })

  val a = io.a
  val b = io.b
  val c = io.c
  val d = io.d
  val max = WireDefault(0.U(4.W))

  // ***** your code starts here *****

  val maxAB = Mux(a > b, a, b)
  val maxCD = Mux(c > d, c, d)

  max := Mux(maxAB > maxCD, maxAB, maxCD)

  // ***** your code ends here *****

  io.max := max
}