FIRRTL version 1.2.0
circuit Majority :
  module Majority :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<1> @[src/main/scala/Majority.scala 8:14]
    input io_b : UInt<1> @[src/main/scala/Majority.scala 8:14]
    input io_c : UInt<1> @[src/main/scala/Majority.scala 8:14]
    output io_out : UInt<1> @[src/main/scala/Majority.scala 8:14]

    io_out <= io_a @[src/main/scala/Majority.scala 29:10]
