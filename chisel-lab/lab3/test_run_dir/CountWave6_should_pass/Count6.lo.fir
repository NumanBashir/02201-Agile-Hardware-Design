FIRRTL version 1.2.0
circuit Count6 :
  module Count6 :
    input clock : Clock
    input reset : UInt<1>
    output io_dout : UInt<8> @[src/main/scala/Count6.scala 4:14]

    reg cntReg : UInt<4>, clock with :
      reset => (UInt<1>("h0"), cntReg) @[src/main/scala/Count6.scala 12:23]
    node _cntReg_T = eq(cntReg, UInt<3>("h6")) @[src/main/scala/Count6.scala 13:24]
    node _cntReg_T_1 = add(cntReg, UInt<1>("h1")) @[src/main/scala/Count6.scala 13:45]
    node _cntReg_T_2 = tail(_cntReg_T_1, 1) @[src/main/scala/Count6.scala 13:45]
    node _cntReg_T_3 = mux(_cntReg_T, UInt<1>("h0"), _cntReg_T_2) @[src/main/scala/Count6.scala 13:16]
    node res = cntReg @[src/main/scala/Count6.scala 8:17 14:7]
    io_dout <= pad(res, 8) @[src/main/scala/Count6.scala 18:11]
    cntReg <= mux(reset, UInt<4>("h0"), _cntReg_T_3) @[src/main/scala/Count6.scala 12:{23,23} 13:10]
