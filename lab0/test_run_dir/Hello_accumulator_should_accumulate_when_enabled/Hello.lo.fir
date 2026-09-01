FIRRTL version 1.2.0
circuit Hello :
  module Hello : @[Hello.scala 11:7]
    input clock : Clock @[Hello.scala 11:7]
    input reset : UInt<1> @[Hello.scala 11:7]
    input io_din : UInt<8> @[Hello.scala 12:14]
    output io_dout : UInt<16> @[Hello.scala 12:14]
    input io_clear : UInt<1> @[Hello.scala 12:14]
    input io_enable : UInt<1> @[Hello.scala 12:14]

    reg accReg : UInt<16>, clock with :
      reset => (UInt<1>("h0"), accReg) @[Hello.scala 19:23]
    node _accReg_T = add(accReg, io_din) @[Hello.scala 24:22]
    node _accReg_T_1 = tail(_accReg_T, 1) @[Hello.scala 24:22]
    node _GEN_0 = mux(io_enable, _accReg_T_1, accReg) @[Hello.scala 23:25 24:12 19:23]
    node _GEN_1 = mux(io_clear, UInt<1>("h0"), _GEN_0) @[Hello.scala 21:18 22:12]
    io_dout <= accReg @[Hello.scala 27:11]
    accReg <= mux(reset, UInt<16>("h0"), _GEN_1) @[Hello.scala 19:{23,23}]
