FIRRTL version 1.2.0
circuit MaxFinder :
  module MaxFinder : @[src/main/scala/MaxFinder.scala 9:7]
    input clock : Clock @[src/main/scala/MaxFinder.scala 9:7]
    input reset : UInt<1> @[src/main/scala/MaxFinder.scala 9:7]
    input io_in_0 : UInt<1> @[src/main/scala/MaxFinder.scala 13:14]
    output io_max : UInt<1> @[src/main/scala/MaxFinder.scala 13:14]

    io_max <= io_in_0 @[src/main/scala/MaxFinder.scala 19:10]
