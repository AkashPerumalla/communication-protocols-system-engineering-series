# Exercise 04 - VSAT Network Design

## Objective
Model a practical hub-spoke VSAT topology and verify site enrollment from deterministic inventory.

## Implementation Steps

1. Call GET /api/vsat.
2. Inspect hub, spokes, site count, and topology type.
3. Correlate output with /api/stations inventory.
4. Identify which sites are available for additional bandwidth assignment.

## Expected Output

- Marker VSAT NETWORK CREATED appears.
- Hub is Hub-Station.
- Spokes include VSAT-Site-1 through VSAT-Site-5.
- Topology type is HUB_SPOKE.

## Solution Walkthrough

VSATNetworkService discovers the HUB role and all VSAT role stations from repository data and constructs a topology view DTO. This is a simplified analog of network planning tools used in enterprise VSAT rollouts and bandwidth assignment operations.
