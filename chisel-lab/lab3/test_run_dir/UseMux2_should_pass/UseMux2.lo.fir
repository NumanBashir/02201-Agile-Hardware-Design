FIRRTL version 1.2.0
circuit UseMux2 :
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

  module UseMux2 :
    input clock : Clock
    input reset : UInt<1>
    input io_sel : UInt<1> @[src/main/scala/UseMux2.scala 4:14]
    output io_dout : UInt<1> @[src/main/scala/UseMux2.scala 4:14]

    inst mux of Mux2 @[src/main/scala/UseMux2.scala 16:19]
    node res = mux.io_y @[src/main/scala/UseMux2.scala 12:17 20:7]
    io_dout <= res @[src/main/scala/UseMux2.scala 24:11]
    mux.clock <= clock
    mux.reset <= reset
    mux.io_a <= UInt<1>("h1") @[src/main/scala/UseMux2.scala 17:12]
    mux.io_b <= UInt<1>("h0") @[src/main/scala/UseMux2.scala 18:12]
    mux.io_sel <= io_sel @[src/main/scala/UseMux2.scala 19:14]
