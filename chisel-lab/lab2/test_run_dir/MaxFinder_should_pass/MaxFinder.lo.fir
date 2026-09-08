FIRRTL version 1.2.0
circuit MaxFinder :
  module MaxFinder :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<4> @[src/main/scala/MaxFinder.scala 4:14]
    input io_b : UInt<4> @[src/main/scala/MaxFinder.scala 4:14]
    input io_c : UInt<4> @[src/main/scala/MaxFinder.scala 4:14]
    input io_d : UInt<4> @[src/main/scala/MaxFinder.scala 4:14]
    output io_max : UInt<4> @[src/main/scala/MaxFinder.scala 4:14]

    node _maxAB_T = gt(io_a, io_b) @[src/main/scala/MaxFinder.scala 20:21]
    node maxAB = mux(_maxAB_T, io_a, io_b) @[src/main/scala/MaxFinder.scala 20:18]
    node _maxCD_T = gt(io_c, io_d) @[src/main/scala/MaxFinder.scala 21:21]
    node maxCD = mux(_maxCD_T, io_c, io_d) @[src/main/scala/MaxFinder.scala 21:18]
    node _max_T = gt(maxAB, maxCD) @[src/main/scala/MaxFinder.scala 23:20]
    node _max_T_1 = mux(_max_T, maxAB, maxCD) @[src/main/scala/MaxFinder.scala 23:13]
    node max = _max_T_1 @[src/main/scala/MaxFinder.scala 16:24 23:7]
    io_max <= max @[src/main/scala/MaxFinder.scala 27:10]
