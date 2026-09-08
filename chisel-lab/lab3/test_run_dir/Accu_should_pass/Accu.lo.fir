FIRRTL version 1.2.0
circuit Accu :
  module Accu :
    input clock : Clock
    input reset : UInt<1>
    input io_din : UInt<8> @[src/main/scala/Accu.scala 4:14]
    input io_setZero : UInt<1> @[src/main/scala/Accu.scala 4:14]
    output io_dout : UInt<8> @[src/main/scala/Accu.scala 4:14]

    reg accReg : UInt<8>, clock with :
      reset => (UInt<1>("h0"), accReg) @[src/main/scala/Accu.scala 14:23]
    node _accReg_T = add(accReg, io_din) @[src/main/scala/Accu.scala 18:22]
    node _accReg_T_1 = tail(_accReg_T, 1) @[src/main/scala/Accu.scala 18:22]
    node _GEN_0 = mux(io_setZero, UInt<1>("h0"), _accReg_T_1) @[src/main/scala/Accu.scala 15:20 16:12 18:12]
    node res = accReg @[src/main/scala/Accu.scala 10:17 20:7]
    io_dout <= res @[src/main/scala/Accu.scala 24:11]
    accReg <= mux(reset, UInt<8>("h0"), _GEN_0) @[src/main/scala/Accu.scala 14:{23,23}]
