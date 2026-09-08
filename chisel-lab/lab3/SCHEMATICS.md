# Lab 3 schematic solutions

## Mux4 construction

```text
a ──┐
    Mux2 ─────┐
b ──┘        │
 sel(0)      Mux2 ── y
c ──┐        │
    Mux2 ─────┘
d ──┘      sel(1)
 sel(0)
```

Each Mux2 selects its upper input for select 0 and lower input for
select 1. Thus sel 00, 01, 10, 11 selects a, b, c, d respectively.

## Code example 1

```text
RED   ── [0] ┐
             MUX ── light
GREEN ── [1] ┘
              ↑
              ok
```

This is combinational logic: ok selects GREEN when true and RED otherwise.
No register is introduced by the when statement.

## Code example 2

```text
 0 ── [sel=0] ┐
11 ── [sel=1] │
22 ── [sel=2] │
33 ── [sel=3] MUX ── dout
44 ── [sel=4] │
55 ── [sel=5] │
 0 ── [other] ┘
               ↑
              sel
```

This is a combinational selection network. WireDefault supplies 0 for
any unmatched selector value; it does not create storage.

## Code example 3

```text
Q ─────────────────────── [sel=0: hold] ┐
0 ─────────────────────── [sel=1: clear]│
Q ── ADD(din) ──────────── [sel=2: add]  MUX ── D [8-bit register] Q
Q ── SUBTRACT(din) ─────── [sel=3: sub] ┘           ↑ clock/reset │
                                         sel       reset → 0    │
↑                                                               │
└────────────────────── feedback ────────────────────────────────┘
```

The mux selects the register's next value. On a clock edge the register
holds, clears, adds din, or subtracts din according to sel. Reset initializes
the register to zero. Unmatched selector values also hold the current value.
The stored result is eight bits, so overflow or underflow wraps modulo 256.

Discuss these schematics with a TA as requested in the README.
