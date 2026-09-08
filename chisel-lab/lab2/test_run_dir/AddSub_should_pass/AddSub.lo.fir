FIRRTL version 1.2.0
circuit AddSub :
  module AddSub :
    input clock : Clock
    input reset : UInt<1>
    input io_a : UInt<4> @[src/main/scala/AddSub.scala 4:14]
    input io_b : UInt<4> @[src/main/scala/AddSub.scala 4:14]
    input io_selAdd : UInt<1> @[src/main/scala/AddSub.scala 4:14]
    output io_y : UInt<4> @[src/main/scala/AddSub.scala 4:14]

    node _res_T = add(io_a, io_b) @[src/main/scala/AddSub.scala 18:24]
    node _res_T_1 = tail(_res_T, 1) @[src/main/scala/AddSub.scala 18:24]
    node _res_T_2 = sub(io_a, io_b) @[src/main/scala/AddSub.scala 18:31]
    node _res_T_3 = tail(_res_T_2, 1) @[src/main/scala/AddSub.scala 18:31]
    node _res_T_4 = mux(io_selAdd, _res_T_1, _res_T_3) @[src/main/scala/AddSub.scala 18:13]
    node res = _res_T_4 @[src/main/scala/AddSub.scala 14:24 18:7]
    io_y <= res @[src/main/scala/AddSub.scala 22:8]
