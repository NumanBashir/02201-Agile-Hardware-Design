FIRRTL version 1.2.0
circuit Mux2 :
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
