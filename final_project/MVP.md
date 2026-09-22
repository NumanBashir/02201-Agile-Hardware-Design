# Project Idea: Parameterized Football Analytics Event Processor

## Overview

The goal of this project is to build a configurable hardware generator in Scala/Chisel for processing structured football event data.

The generated hardware will receive a stream of encoded football events and maintain selected match statistics as the events arrive.

The project does **not** attempt to detect football events directly from video, cameras, or tracking data. Instead, it assumes that an upstream system has already converted match activity into structured events.

The focus of the project is therefore on:

- Hardware generation
- Event decoding
- Hardware counters
- Parameterization
- Testing and verification

Instead of building one fixed circuit, the project will use Chisel and Scala parameters so that different hardware configurations can be generated from the same source code.

---

## Intended Use Case

A possible real-world use case is a football analytics pipeline where an external provider or analysis system produces structured match events.

Conceptually:

```text
Football match
      ↓
External event provider / tracking system
      ↓
Structured football events
      ↓
Football Analytics Event Processor
      ↓
Updated statistics
      ↓
Dashboard / analysis software
```

For example, an upstream system could identify the following events:

```text
Pass by Team 0
Pass by Team 0
Shot by Team 1
Defensive action by Team 0
```

The hardware processor would receive encoded versions of these events and update the corresponding counters.

The processor could therefore continuously maintain statistics such as:

```text
Team 0:
Passes = 83
Shots = 7
Defensive actions = 11

Team 1:
Passes = 65
Shots = 4
Defensive actions = 14
```

---

## Data Source

For the MVP, we do **not** require access to a commercial football data provider.

Instead, football events will be generated inside the testbench as a controlled event stream.

Example:

```text
(team = 0, event = PASS)
(team = 0, event = PASS)
(team = 1, event = SHOT)
(team = 0, event = DEFENSIVE_ACTION)
```

This makes it possible to verify the hardware against known expected outputs.

For example, after the above events the expected result would be:

```text
Team 0:
Passes = 2
Shots = 0
Defensive actions = 1

Team 1:
Passes = 0
Shots = 1
Defensive actions = 0
```

A later extension could use an open football event dataset or an external data source and convert those events into the input format expected by the hardware.

The acquisition and detection of football events is therefore outside the scope of the MVP.

---

## Input Events

Each football event can contain information such as:

- Team ID
- Player ID
- Event type
- Pitch zone
- Valid signal

For the MVP, only the fields required for team-level event counting are necessary.

A simplified event could therefore be:

```text
teamId
eventType
valid
```

Example:

```text
teamId    = 0
eventType = PASS
valid     = 1
```

The first version will support a small set of event types:

- Pass
- Shot
- Defensive action

Internally, these can be encoded numerically.

Example:

```text
0 = PASS
1 = SHOT
2 = DEFENSIVE_ACTION
```

The event types are defined by the project for the MVP. The design does not need to match the complete event taxonomy of a professional football data provider.

---

## What "Real-Time" Means

In this project, "real-time" means that events are processed incrementally as they arrive rather than processing an entire match after it has finished.

For example:

```text
Clock cycle 100:
PASS event for Team 0 arrives

Clock cycle 101:
Team 0 pass counter has been incremented
```

The hardware can therefore continuously update statistics while receiving an event stream.

The project does not claim to perform real-time computer vision or event detection.

The intended processing flow is:

```text
Event input
    ↓
Decode team and event type
    ↓
Select corresponding counter
    ↓
Increment counter
    ↓
Updated statistics output
```

---

## MVP

The minimum viable product will process incoming structured football events and maintain simple team-level counters.

The MVP should be able to:

- Receive an encoded event
- Check whether the event is valid
- Identify the team
- Decode the event type
- Increment the correct hardware counter
- Output the current statistics
- Reset counters when required
- Be tested using a controlled sequence of simulated football events

Example:

```text
Input event:

Team = 0
Event type = PASS
Valid = 1

Result:

Team 0 pass counter is incremented
```

The MVP will initially support:

```text
2 teams

3 event types:
- Pass
- Shot
- Defensive action
```

This keeps the first implementation small enough to complete and verify before adding more advanced functionality.

---

## Hardware Generator

The circuit should be configurable rather than fixed.

Possible generator parameters include:

```scala
class FootballAnalytics(
  numberOfTeams: Int,
  numberOfEventTypes: Int,
  counterWidth: Int
) extends Module
```

For the MVP, these are the most important parameters.

A possible configuration could be:

```scala
new FootballAnalytics(
  numberOfTeams = 2,
  numberOfEventTypes = 3,
  counterWidth = 16
)
```

The same generator could later generate another configuration such as:

```scala
new FootballAnalytics(
  numberOfTeams = 4,
  numberOfEventTypes = 6,
  counterWidth = 32
)
```

This demonstrates that the project is a hardware generator rather than one fixed hardware implementation.

Additional parameters can be introduced later if needed, for example:

```text
numberOfPlayers
numberOfZones
enabledStatistics
```

---

## Why Hardware?

A normal CPU could easily calculate simple football statistics, so the purpose of this project is not to argue that football statistics require hardware acceleration.

The football application is used as a clear and understandable example for exploring parameterized streaming hardware.

The project demonstrates how hardware can:

- Process events as they arrive
- Update counters with low latency
- Perform multiple operations in parallel
- Generate different hardware configurations from the same Chisel source code

The same architecture and generator principles could also be applied to much higher-rate event streams where hardware acceleration would be more important.

---

## Verification

The initial hardware will be verified using a Chisel testbench.

A known sequence of football events will be provided as input.

For example:

```text
PASS Team 0
PASS Team 0
SHOT Team 1
DEFENSIVE_ACTION Team 0
```

The test will then check that the hardware counters match the expected values.

A later version could include a simple Scala software model that processes the same event stream.

The hardware output could then be compared against the software model.

Conceptually:

```text
                 ┌──> Chisel hardware ──┐
Event stream ────┤                      ├──> Compare results
                 └──> Scala model ──────┘
```

---

## Possible Extensions

After the MVP is working, the project can be extended incrementally.

Possible extensions include:

1. Configurable number of event types
2. Player-specific statistics
3. Statistics for different pitch zones
4. Additional event types
5. PPDA-related counters
6. Configurable enabled statistics
7. Software reference model
8. Co-simulation
9. Formal verification
10. Input from an open football event dataset

---

## PPDA Extension

A later version could support statistics related to PPDA.

The hardware could maintain counters such as:

```text
Opponent passes
Defensive actions
```

A simplified PPDA value can then be calculated as:

```text
PPDA = Opponent passes / Defensive actions
```

For the first version, the hardware does not need to perform the division itself.

The hardware can instead expose the two counter values and allow software to calculate the ratio.

If time permits, fixed-point division could later be explored as an optional hardware extension.

---

## Scope Clarifications

The following are intentionally outside the scope of the MVP:

- Detecting football events from video
- Computer vision
- Player tracking
- Direct integration with commercial providers such as Wyscout or Opta
- Building a full football analytics platform
- Calculating complex metrics such as xG
- Supporting every possible football event type

The MVP focuses only on the hardware processing stage after structured events already exist.

---

## Development Approach

The project will be developed incrementally using an Agile/Scrum-inspired approach.

A possible development plan is:

```text
Sprint 1 -> Basic team event counters
Sprint 2 -> Parameterized teams, event types, and counter width
Sprint 3 -> Scala reference model and stronger testing
Sprint 4 -> Player-specific statistics
Sprint 5 -> Pitch zones and PPDA-related counters
Sprint 6 -> Co-simulation, verification, and final refinements
```

The initial focus is to create a small working design that can already be tested and demonstrated before adding more advanced functionality.
