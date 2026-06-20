# Exercise 01 - SatCom Link Overview

## Objective
Understand the modeled satellite link inventory, endpoint structure, and NOC perspective for hub-to-remote VSAT connectivity.

## Implementation Steps

1. Start the application.
2. Call GET /api/satellites and inspect orbital slots and status.
3. Call GET /api/transponders and review frequencies and polarization.
4. Call GET /api/stations and verify station roles (HUB/VSAT).
5. Call GET /api/links and map each site to serving satellite.

## Expected Output

- Marker SATELLITE NETWORK ACTIVE appears in responses.
- Three deterministic satellites are listed.
- Six earth stations appear with realistic coordinates and antenna sizes.
- Five links show up/down/degraded mix.

## Solution Walkthrough

This exercise validates the static network layer of the SatCom NOC. The controller delegates to SatelliteNetworkService, which reads deterministic entities from H2 and maps them into DTOs for a stable API contract. In real NOC tooling, this is equivalent to inventory and topology synchronization before operational analytics begin.
