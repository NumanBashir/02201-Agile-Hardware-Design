# Chisel Generator with Generics

Chisel can use generics to create more flexible and reusable hardware components (like the `Mux` component in Chisel). By parameterizing your modules with type parameters, you can easily adapt them to different data types and widths.

## Example

Here's an example of the syntax for a generic Chisel module:

```scala
class GenericComponent[T <: Data](gen: T) extends Module {
  val io = IO(new Bundle {
    val in = Input(gen)
    val out = Output(gen)
  })

  ???
}
```

In this example, `GenericComponent` can be instantiated with any data type that extends `Data`, allowing for a wide range of use cases.

## Exercise

Implement a generic component that performs a simple operation. Add tests to verify its functionality with different data types.

Feel free to decide on your own component. However, if you need some ideas:
- A generic 4:1 multiplexer
- A generic buffer with read/write signals and indication of empty/full
- A generic FIFO queue with configurable depth (a bit advanced, could use the buffer from above)

* Emit Verilog for your design to synthesize it to an FPGA or ASIC.

## Implemented solution: generic 4:1 multiplexer

`GenericMux4[T <: Data](gen: T)` selects `io.in(io.sel)` and drives `io.out`.
The two-bit selector maps 0, 1, 2, 3 to the four inputs. It is combinational,
so no clock step is required to observe a selected value.

The type is chosen when generating the hardware, for example:

```scala
Module(new GenericMux4(UInt(8.W)))
Module(new GenericMux4(SInt(16.W)))
Module(new GenericMux4(Bool()))
```

Bundles of data fields work too: all fields of the selected input travel
together. All four inputs and the output have the type and width given by
`gen`. The selector chooses a value at runtime; it does not change its type.

Run these commands from `agile-hw/lab3`:

```sh
sbt "testOnly GenericMux4Test"
sbt "runMain GenericMux4Verilog"
```

The six tests cover UInt at 1, 8, and 16 bits, signed eight-bit values,
every Boolean input pattern, and a Bundle with data, tag, and valid fields.
They exercise every selector, unsigned boundaries and random values, and
signed extremes. Explicit `testOnly` reruns the suite even if sbt's
incremental `test` command would skip unchanged tests.

The generator emits an eight-bit UInt instance to `generated/GenericMux4.sv`
using the Chisel 6 SystemVerilog API. The output implements selection logic
without registers. Synthesis and FPGA programming require a separate tool flow.
