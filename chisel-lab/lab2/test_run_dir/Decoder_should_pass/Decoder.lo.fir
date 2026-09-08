FIRRTL version 1.2.0
circuit Decoder :
  module Decoder :
    input clock : Clock
    input reset : UInt<1>
    input io_sel : UInt<2> @[src/main/scala/Decoder.scala 5:14]
    output io_out : UInt<4> @[src/main/scala/Decoder.scala 5:14]

    node _T = eq(UInt<1>("h0"), io_sel) @[src/main/scala/Decoder.scala 15:15]
    node _T_1 = eq(UInt<1>("h1"), io_sel) @[src/main/scala/Decoder.scala 15:15]
    node _T_2 = eq(UInt<2>("h2"), io_sel) @[src/main/scala/Decoder.scala 15:15]
    node _T_3 = eq(UInt<2>("h3"), io_sel) @[src/main/scala/Decoder.scala 15:15]
    node _GEN_0 = mux(_T_3, UInt<4>("h8"), UInt<1>("h0")) @[src/main/scala/Decoder.scala 15:15 19:17 11:24]
    node _GEN_1 = mux(_T_2, UInt<3>("h4"), _GEN_0) @[src/main/scala/Decoder.scala 15:15 18:17]
    node _GEN_2 = mux(_T_1, UInt<2>("h2"), _GEN_1) @[src/main/scala/Decoder.scala 15:15 17:17]
    node _GEN_3 = mux(_T, UInt<1>("h1"), _GEN_2) @[src/main/scala/Decoder.scala 15:15 16:17]
    node dec = _GEN_3 @[src/main/scala/Decoder.scala 11:24]
    io_out <= dec @[src/main/scala/Decoder.scala 24:10]
