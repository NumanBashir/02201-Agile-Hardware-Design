# Course reference checkout

`agile-hw/` inside this directory is an independent clone of
https://github.com/schoeberl/agile-hw.git and is ignored by the enclosing
student repository. Use it for current course instructions and upstream history.
Keep solutions in the top-level `../agile-hw/`, tracked by the student repository.

From the student repository root, update the reference with:

```sh
git -C references/agile-hw pull --ff-only
git -C references/agile-hw log -5 --oneline
```

If this reference checkout is missing after cloning the student repository:

```sh
git clone https://github.com/schoeberl/agile-hw.git references/agile-hw
```

Do not solve exercises in the reference checkout. Inspect updates there and
selectively copy new starter files into the working copy after reviewing any
existing solution at the destination. Do not copy the whole tree over solutions.

## Lab numbering aligned

On 2026-09-22, the working copy was renumbered to match upstream commit
`234c671`, preserving every file in each moved directory:

| Exercise | Previous working folder | Current working folder | Reference folder |
| --- | --- | --- | --- |
| MaxFinder (completed) | `agile-hw/lab4` | `agile-hw/lab2` | `references/agile-hw/lab2` |
| Generic multiplexer (completed) | `agile-hw/lab5` | `agile-hw/lab3` | `references/agile-hw/lab3` |
| CSR adapter (starter) | `agile-hw/lab11` | `agile-hw/lab4` | `references/agile-hw/lab4` |

The separate `chisel-lab/` directory retains its original numbering.
The working course overview reflects these lab moves and week 5 project
presentations. Consult the reference README for the latest reading list and
schedule. Renumbering did not import upstream implementation or test changes.
