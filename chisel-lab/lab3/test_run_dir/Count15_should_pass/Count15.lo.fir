FIRRTL version 1.2.0
circuit Count15 :
  module Count15 :
    input clock : Clock
    input reset : UInt<1>
    output io_dout : UInt<8> @[src/main/scala/Count15.scala 4:14]

    reg cntReg : UInt<4>, clock with :
      reset => (UInt<1>("h0"), cntReg) @[src/main/scala/Count15.scala 12:23]
    node _cntReg_T = add(cntReg, UInt<1>("h1")) @[src/main/scala/Count15.scala 14:20]
    node _cntReg_T_1 = tail(_cntReg_T, 1) @[src/main/scala/Count15.scala 14:20]
    node res = cntReg @[src/main/scala/Count15.scala 8:17 15:7]
    io_dout <= pad(res, 8) @[src/main/scala/Count15.scala 19:11]
    cntReg <= mux(reset, UInt<4>("h0"), _cntReg_T_1) @[src/main/scala/Count15.scala 12:{23,23} 14:10]
