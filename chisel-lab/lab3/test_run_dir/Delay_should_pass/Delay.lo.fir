FIRRTL version 1.2.0
circuit Delay :
  module Delay :
    input clock : Clock
    input reset : UInt<1>
    input io_din : UInt<8> @[src/main/scala/Delay.scala 4:14]
    output io_dout : UInt<8> @[src/main/scala/Delay.scala 4:14]

    reg firstReg : UInt<8>, clock with :
      reset => (UInt<1>("h0"), firstReg) @[src/main/scala/Delay.scala 13:25]
    reg secondReg : UInt<8>, clock with :
      reset => (UInt<1>("h0"), secondReg) @[src/main/scala/Delay.scala 14:26]
    node res = secondReg @[src/main/scala/Delay.scala 9:17 17:7]
    io_dout <= res @[src/main/scala/Delay.scala 21:11]
    firstReg <= mux(reset, UInt<8>("h0"), io_din) @[src/main/scala/Delay.scala 13:{25,25} 15:12]
    secondReg <= mux(reset, UInt<8>("h0"), firstReg) @[src/main/scala/Delay.scala 14:{26,26} 16:13]
