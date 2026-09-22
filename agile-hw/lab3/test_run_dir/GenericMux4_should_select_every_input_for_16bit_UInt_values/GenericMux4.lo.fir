FIRRTL version 1.2.0
circuit GenericMux4 :
  module GenericMux4 : @[src/main/scala/GenericMux4.scala 4:7]
    input clock : Clock @[src/main/scala/GenericMux4.scala 4:7]
    input reset : UInt<1> @[src/main/scala/GenericMux4.scala 4:7]
    input io_in_0 : UInt<16> @[src/main/scala/GenericMux4.scala 5:14]
    input io_in_1 : UInt<16> @[src/main/scala/GenericMux4.scala 5:14]
    input io_in_2 : UInt<16> @[src/main/scala/GenericMux4.scala 5:14]
    input io_in_3 : UInt<16> @[src/main/scala/GenericMux4.scala 5:14]
    input io_sel : UInt<2> @[src/main/scala/GenericMux4.scala 5:14]
    output io_out : UInt<16> @[src/main/scala/GenericMux4.scala 5:14]

    node _GEN_0 = validif(eq(UInt<1>("h0"), io_sel), io_in_0) @[src/main/scala/GenericMux4.scala 12:{10,10}]
    node _GEN_1 = mux(eq(UInt<1>("h1"), io_sel), io_in_1, _GEN_0) @[src/main/scala/GenericMux4.scala 12:{10,10}]
    node _GEN_2 = mux(eq(UInt<2>("h2"), io_sel), io_in_2, _GEN_1) @[src/main/scala/GenericMux4.scala 12:{10,10}]
    node _GEN_3 = mux(eq(UInt<2>("h3"), io_sel), io_in_3, _GEN_2) @[src/main/scala/GenericMux4.scala 12:{10,10}]
    node _io_in_io_sel = _GEN_3 @[src/main/scala/GenericMux4.scala 12:10]
    io_out <= _io_in_io_sel @[src/main/scala/GenericMux4.scala 12:10]
