FIRRTL version 1.2.0
circuit Majority :
  module Majority :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<1> @[src/main/scala/Majority.scala 8:14]
    input io_b : UInt<1> @[src/main/scala/Majority.scala 8:14]
    input io_c : UInt<1> @[src/main/scala/Majority.scala 8:14]
    output io_out : UInt<1> @[src/main/scala/Majority.scala 8:14]

    node _res_T = and(io_a, io_b) @[src/main/scala/Majority.scala 24:16]
    node _res_T_1 = and(io_a, io_c) @[src/main/scala/Majority.scala 24:26]
    node _res_T_2 = or(_res_T, _res_T_1) @[src/main/scala/Majority.scala 24:21]
    node _res_T_3 = and(io_b, io_c) @[src/main/scala/Majority.scala 24:36]
    node res = or(_res_T_2, _res_T_3) @[src/main/scala/Majority.scala 24:31]
    io_out <= res @[src/main/scala/Majority.scala 30:10]
