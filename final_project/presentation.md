### Presentation flow / keywords

**1. Idea**
“Parameterized football analytics event processor.”

**2. Purpose**
“Not just counting football stats — the main goal is to build a configurable event-stream hardware generator in Chisel.”

**3. Input**
“Structured events such as team ID + event type + valid signal.”

**4. Processing**
“Decode the event → route it → increment the correct generated counter.”

**5. Output**
“Continuously updated statistics.”

**6. Generator aspect**
“Parameters such as number of teams, event types and counter width decide what hardware is generated.”

**7. MVP**
“Start simple: 2 teams, 3 event types, testbench-generated events.”

**8. First milestone**
“Working decoder and counters, verified against expected results or a simple Scala model.”

**9. Extensions**
“Player stats, zones, PPDA-related counters, more event types.”

A short script:

> “Our project is a parameterized football analytics event processor. The main idea is not just to count football statistics, but to build a configurable event-stream hardware generator in Chisel.
>
> The hardware receives structured events, for example a team ID and whether the event is a pass, shot or defensive action. It decodes the event and updates the corresponding generated counter.
>
> The generator parameters, such as number of teams, event types and counter width, determine what hardware is created. For the MVP, we will keep it simple with two teams and three event types, and generate the events in the testbench.
>
> Our first milestone is to get the decoder and counters working and verify them against expected results. Later we can extend it with players, pitch zones and PPDA-related statistics.”

### Potential objections

**Objection 1: “Isn’t this just a counter app?”**
**Answer:** “The MVP uses counters, but the actual project is the configurable generator that creates the event-processing architecture based on parameters.”

**Objection 2: “Why do this in hardware? A CPU can do it easily.”**
**Answer:** “Yes, for football-scale data a CPU is enough. The football use case is mainly an intuitive way to demonstrate parameterized streaming hardware and generator design.”

**Objection 3: “Where does the football data come from?”**
**Answer:** “For the MVP, we generate structured events in the testbench. A real event provider could be integrated later.”

**Objection 4: “Are you detecting passes and shots from video?”**
**Answer:** “No. Event detection is outside the scope. We assume structured events already exist.”

**Objection 5: “What do you mean by real time?”**
**Answer:** “Events are processed incrementally as they arrive, potentially one event per clock cycle, instead of analysing the entire match afterwards.”

**Objection 6: “What exactly makes it a generator?”**
**Answer:** “Changing parameters like teams, event types or counter width generates a different hardware structure from the same Chisel source.”

**Objection 7: “Why football?”**
**Answer:** “Football gives an easy-to-understand event stream with natural parameters like teams, players, event types and zones.”

**Objection 8: “What if the project is too simple?”**
**Answer:** “The MVP is intentionally simple. The architecture can be extended with configurable event types, player-level statistics, zones, PPDA counters, co-simulation and verification.”

**Objection 9: “What if you don’t have access to Wyscout/Opta data?”**
**Answer:** “We do not depend on them. Testbench-generated events are enough for the MVP.”

**Objection 10: “How do you verify it?”**
**Answer:** “Feed a known event sequence into the hardware and compare the output counters with expected values or a simple Scala reference model.”

The five words I’d memorize are:

**event stream → decode → counters → parameters → verification**
