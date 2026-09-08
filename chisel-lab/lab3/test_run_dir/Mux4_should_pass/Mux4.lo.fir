FIRRTL version 1.2.0
circuit Mux4 :
  module Mux2 :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<1> @[src/main/scala/Mux2.scala 9:14]
    input io_b : UInt<1> @[src/main/scala/Mux2.scala 9:14]
    input io_sel : UInt<1> @[src/main/scala/Mux2.scala 9:14]
    output io_y : UInt<1> @[src/main/scala/Mux2.scala 9:14]

    node _T = eq(io_sel, UInt<1>("h1")) @[src/main/scala/Mux2.scala 17:16]
    node _GEN_0 = mux(_T, io_b, io_a) @[src/main/scala/Mux2.scala 17:25 18:10 16:8]
    io_y <= _GEN_0

  module Mux4 :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<1> @[src/main/scala/Mux4.scala 8:14]
    input io_b : UInt<1> @[src/main/scala/Mux4.scala 8:14]
    input io_c : UInt<1> @[src/main/scala/Mux4.scala 8:14]
    input io_d : UInt<1> @[src/main/scala/Mux4.scala 8:14]
    input io_sel : UInt<2> @[src/main/scala/Mux4.scala 8:14]
    output io_y : UInt<1> @[src/main/scala/Mux4.scala 8:14]

    inst muxAB of Mux2 @[src/main/scala/Mux4.scala 19:21]
    inst muxCD of Mux2 @[src/main/scala/Mux4.scala 20:21]
    inst muxOut of Mux2 @[src/main/scala/Mux4.scala 21:22]
    node _muxAB_io_sel_T = bits(io_sel, 0, 0) @[src/main/scala/Mux4.scala 26:25]
    node _muxCD_io_sel_T = bits(io_sel, 0, 0) @[src/main/scala/Mux4.scala 29:25]
    node _muxOut_io_sel_T = bits(io_sel, 1, 1) @[src/main/scala/Mux4.scala 34:26]
    io_y <= muxOut.io_y @[src/main/scala/Mux4.scala 35:8]
    muxAB.clock <= clock
    muxAB.reset <= reset
    muxAB.io_a <= io_a @[src/main/scala/Mux4.scala 24:14]
    muxAB.io_b <= io_b @[src/main/scala/Mux4.scala 25:14]
    muxAB.io_sel <= _muxAB_io_sel_T @[src/main/scala/Mux4.scala 26:16]
    muxCD.clock <= clock
    muxCD.reset <= reset
    muxCD.io_a <= io_c @[src/main/scala/Mux4.scala 27:14]
    muxCD.io_b <= io_d @[src/main/scala/Mux4.scala 28:14]
    muxCD.io_sel <= _muxCD_io_sel_T @[src/main/scala/Mux4.scala 29:16]
    muxOut.clock <= clock
    muxOut.reset <= reset
    muxOut.io_a <= muxAB.io_y @[src/main/scala/Mux4.scala 32:15]
    muxOut.io_b <= muxCD.io_y @[src/main/scala/Mux4.scala 33:15]
    muxOut.io_sel <= _muxOut_io_sel_T @[src/main/scala/Mux4.scala 34:17]
