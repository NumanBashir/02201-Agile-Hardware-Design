# MaxFinder Exercise

In this exercise, you will implement a Chisel module that finds the maximum value in a vector of unsigned integers using a tree-based reduction (`reduceTree`).

## Steps

1. Implement a `MaxFinder` module that takes a `Vec` of `UInt` values as input and outputs the maximum value.
	- Use Chisel's `reduceTree` or a similar tree-based approach for efficiency.

2. Write a `ChiselTest` for your `MaxFinder` to verify its functionality:
	- Test with different input vectors to ensure the correct maximum is found.
	- Include edge cases, such as all zeros or all equal values.

3. (Optional) Extend the circuit to also return the index of the maximum value. Hint: lookup `zipWithIndex` and use tuples. Use `reduce` to find both the maximum value and its index.

4. Emit Verilog for your `MaxFinder` module and explore the generated code. You can try to synthesize it.

## Running this solution

Run these commands from this `agile-hw/lab2` directory:

```sh
sbt "testOnly MaxFinderTest"
sbt "runMain MaxFinderVerilog"
```

The explicit `testOnly` command runs all six tests even when sbt's incremental
`test` command skips unchanged tests. Tests cover zeros, equal values, unsigned
boundaries, each maximum position, and 500 reproducible random vectors across
different input counts and bit widths, including a single input and odd counts.

`MaxFinder(n, width)` is combinational: no clock step is needed to observe the
maximum after changing its inputs. `reduceTree` builds a tree of comparisons
and multiplexers. Both parameters must be positive.

The generator emits a four-input, eight-bit example to `generated/MaxFinder.sv`
using the Chisel 6 SystemVerilog API. Inspect its three comparisons: two find
the winners of the input pairs, and the last finds the overall winner. No
registers or sequential latency are introduced. FPGA synthesis is optional.

The optional maximum-index extension is not included.
