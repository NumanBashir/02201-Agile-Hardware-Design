FIRRTL version 1.2.0
circuit MaxFinder :
  module MaxFinder : @[src/main/scala/MaxFinder.scala 9:7]
    input clock : Clock @[src/main/scala/MaxFinder.scala 9:7]
    input reset : UInt<1> @[src/main/scala/MaxFinder.scala 9:7]
    input io_in_0 : UInt<4> @[src/main/scala/MaxFinder.scala 13:14]
    input io_in_1 : UInt<4> @[src/main/scala/MaxFinder.scala 13:14]
    output io_max : UInt<4> @[src/main/scala/MaxFinder.scala 13:14]

    node _io_max_T = gt(io_in_0, io_in_1) @[src/main/scala/MaxFinder.scala 19:46]
    node _io_max_T_1 = mux(_io_max_T, io_in_0, io_in_1) @[src/main/scala/MaxFinder.scala 19:43]
    io_max <= _io_max_T_1 @[src/main/scala/MaxFinder.scala 19:10]
