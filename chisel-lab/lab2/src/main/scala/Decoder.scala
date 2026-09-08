import chisel3._
import chisel3.util._

class Decoder extends Module {
  val io = IO(new Bundle {
    val sel = Input(UInt(2.W))
    val out = Output(UInt(4.W))
  })

  val sel = io.sel
  val dec = WireDefault(0.U)

  // ***** your code starts here *****

  switch(sel) {
  is(0.U) { dec := 1.U }
  is(1.U) { dec := 2.U }
  is(2.U) { dec := 4.U }
  is(3.U) { dec := 8.U }
}

  // ***** your code ends here *****

  io.out := dec
}


