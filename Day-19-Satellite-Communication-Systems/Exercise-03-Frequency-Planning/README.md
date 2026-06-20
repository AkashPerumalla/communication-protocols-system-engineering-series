# Exercise 03 - Frequency Planning

## Objective
Generate channel plans per transponder and validate guard-band spacing to avoid adjacent-channel interference.

## Implementation Steps

1. Call GET /api/frequency-plan and inspect existing channels.
2. Send POST /api/frequency-plan/generate for INSAT-4A transponder 1.
3. Use channelBandwidth and guardBand settings to create three channels.
4. Re-run with tighter guard bands and observe validation message.

## Expected Output

- Marker FREQUENCY PLAN GENERATED is returned.
- New channels are allocated with deterministic naming and spacing.
- Service reports whether guard bands are valid or overlapping.

## Solution Walkthrough

FrequencyPlanningService finds the target transponder, derives channel frequency offsets, persists the generated plan, and then validates uplink adjacency. This models real spectrum planning workflows where capacity must be added while keeping safe separation between carriers.
