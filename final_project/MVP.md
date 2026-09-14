# Project Idea: Parameterized Football Analytics Event Processor

## Overview

The goal of this project is to build a configurable hardware generator for processing football event data.

The generated hardware will receive a stream of football events and keep track of selected match statistics in real time.

Instead of building one fixed circuit, the project will use Chisel and Scala parameters so that different hardware configurations can be generated from the same source code.

## Input Events

Each football event can contain information such as:

- Team ID
- Player ID
- Event type
- Pitch zone
- Valid signal

The first version will support a small set of event types:

- Pass
- Shot
- Defensive action

## MVP

The minimum viable product will process incoming football events and maintain simple team-level counters.

Example:

```text
Input event:
Team = 0
Event type = PASS
Valid = 1

Result:
Team 0 pass counter is incremented
```

The first version should therefore be able to:

- Receive an event
- Identify the team
- Decode the event type
- Increment the correct counter
- Output the current statistics

Example statistics:

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

## Hardware Generator

The circuit should be configurable rather than fixed.

Possible generator parameters include:

```scala
class FootballAnalytics(
  numberOfTeams: Int,
  numberOfPlayers: Int,
  numberOfEventTypes: Int,
  numberOfZones: Int,
  counterWidth: Int
) extends Module
```

This allows the same Chisel code to generate different hardware configurations.

For example:

```scala
new FootballAnalytics(
  numberOfTeams = 2,
  numberOfPlayers = 11,
  numberOfEventTypes = 3,
  numberOfZones = 4,
  counterWidth = 16
)
```

## Possible Extensions

After the MVP is working, the project can be extended incrementally.

Possible extensions include:

1. Configurable number of event types
2. Player-specific statistics
3. Statistics for different pitch zones
4. Additional event types
5. PPDA-related counters
6. More advanced testing
7. Co-simulation with a Scala reference model
8. Formal verification

## PPDA Extension

A later version could support statistics related to PPDA.

Instead of initially performing the division directly in hardware, the circuit can expose:

```text
Opponent passes
Defensive actions
```

The PPDA value can then be calculated externally:

```text
PPDA = Opponent passes / Defensive actions
```

Hardware-based fixed-point division can be considered as an optional extension later.

## Development Approach

The project will be developed incrementally using an Agile/Scrum-inspired approach.

A possible development plan is:

```text
Sprint 1 -> Team event counters
Sprint 2 -> Configurable event types
Sprint 3 -> Player-specific statistics
Sprint 4 -> Pitch zones
Sprint 5 -> PPDA-related counters
Sprint 6 -> Testing, co-simulation and verification
```

The initial focus will be on creating a simple working MVP before adding more advanced functionality.

```
This is a good level for the repo right now: clear enough to show the idea, but not overcommitting to features before we know how complex the implementation becomes.
```
