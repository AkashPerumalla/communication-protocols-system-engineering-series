# Day-19 - Satellite Communication Systems

This module builds a realistic Spring Boot Satellite Network Operations Center (NOC) simulation used to monitor, analyze, plan, troubleshoot, and manage VSAT satellite links.

## What This Module Teaches

- Satellite communication architecture and NOC workflows
- Uplink and downlink chain fundamentals
- Link budget engineering calculations
- Frequency planning with guard-band validation
- VSAT hub-spoke network design
- Satellite link monitoring and KPI analysis
- Alarm generation and severity handling
- Root-cause-driven troubleshooting workflows
- SatCom protocol-style operational integration
- Dashboard-based NOC decision support

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Web
- Spring Data JPA
- Spring Validation
- H2 Database
- Lombok
- Maven

## Project Layout

- src/main/java/com/sky2dev/day19
- controller: REST APIs
- service: domain logic and calculators
- repository: JPA persistence interfaces
- model: entities and enums
- dto: API contracts and request/response models
- config: deterministic seed loading
- src/test/java/com/sky2dev/day19: integration tests
- scripts/run_all_tests.sh: end-to-end marker validation harness
- diagrams: architecture and workflow Mermaid diagrams
- Exercise-01 to Exercise-08: practical guided labs

## Satellite Architecture Overview

The platform models a real SatCom NOC where a HUB earth station routes traffic via geostationary satellites to remote VSAT sites.

1. Uplink carriers originate at hub and gateway stations.
2. Satellite transponders perform bent-pipe frequency translation.
3. Downlink carriers terminate at VSAT sites.
4. Modems publish link metrics (C/N, Eb/No, BER, Rx power, lock).
5. Monitoring services evaluate link health and degradations.
6. Alarm service maps threshold violations to severity.
7. Troubleshooting service performs deterministic RCA.
8. Dashboard aggregates link and alarm KPIs for operators.

## Uplink and Downlink Concepts

- Uplink: earth station to satellite, typically in C/Ku/Ka uplink bands.
- Downlink: satellite to earth station, translated to paired downlink frequency.
- Link quality drivers: path loss, rain attenuation, antenna gain, EIRP, G/T.
- Operational observables: C/N, Eb/No, BER, carrier lock status, throughput, latency.

## Transponders

Each transponder in this model includes:

- transponder number
- 36 MHz class bandwidth examples
- uplink/downlink center frequencies
- polarization mode
- allocation status

## VSAT Topology

This day uses a deterministic hub-spoke topology:

- Hub-Station
- VSAT-Site-1
- VSAT-Site-2
- VSAT-Site-3
- VSAT-Site-4
- VSAT-Site-5

## Link Budget Model

The service calculates key SatCom values:

- EIRP = Tx Power + Tx Antenna Gain - Implementation Loss
- Path Loss (FSPL) = 92.45 + 20 log10(f_GHz) + 20 log10(range_km)
- Carrier Power = EIRP - Path Loss + Antenna Gain
- C/N from carrier power, noise temperature, and G/T
- Link Margin = C/N - Required C/N

Marker: LINK BUDGET CALCULATED

## Frequency Planning

Frequency planning service supports:

- channel allocation per transponder
- generated uplink/downlink pairs
- bandwidth and guard-band spacing
- overlap validation

Marker: FREQUENCY PLAN GENERATED

## Monitoring, Alarms, and Troubleshooting

Monitoring checks:

- C/N degradation
- BER increase
- Eb/No degradation
- Rx power drop
- loss of carrier lock

Alarms generated for thresholds:

- C/N low
- BER high
- Eb/No low
- Rx power low
- lock lost

Severity:

- WARNING
- MAJOR
- CRITICAL

Troubleshooting RCA examples:

- Low Rx power + high BER => antenna misalignment
- Rain attenuation profile => rain fade
- Lock lost => check LNB, BUC, modem chain

Markers:

- LINK MONITORING ACTIVE
- SATCOM ALARM GENERATED
- ROOT CAUSE IDENTIFIED

## NOC Dashboard

Dashboard aggregates:

- active links
- active alarms
- critical alarms
- link availability percent
- average C/N
- average Eb/No
- average BER

Marker: SATCOM NOC DASHBOARD

## Deterministic Seed Data

Satellites:

- INSAT-4A
- GSAT-30
- INTELSAT-20

Stations:

- Hub-Station
- VSAT-Site-1 to VSAT-Site-5

Metrics include healthy and critical profiles:

- Healthy: C/N 16 dB, Eb/No 12 dB, BER 1E-7
- Critical: C/N 7 dB, Eb/No 4.8 dB, BER 3.2E-4

## REST APIs

GET endpoints:

- /api/satellites
- /api/transponders
- /api/stations
- /api/links
- /api/link-budget
- /api/frequency-plan
- /api/metrics
- /api/alarms
- /api/troubleshooting
- /api/vsat
- /api/dashboard

POST endpoints:

- /api/link-budget/calculate
- /api/frequency-plan/generate
- /api/alarms/simulate
- /api/troubleshooting/analyze

## Marker Strings

- SATELLITE NETWORK ACTIVE
- LINK BUDGET CALCULATED
- FREQUENCY PLAN GENERATED
- LINK MONITORING ACTIVE
- SATCOM ALARM GENERATED
- ROOT CAUSE IDENTIFIED
- VSAT NETWORK CREATED
- SATCOM NOC DASHBOARD

## How to Run

Build and test:

```bash
mvn -q clean test
```

Run app:

```bash
mvn -q -DskipTests spring-boot:run
```

Run full deterministic validation:

```bash
bash scripts/run_all_tests.sh
```

Import Postman collection:

```bash
Open scripts/day19-satcom-api.postman_collection.json in Postman
```

Set `baseUrl` variable to your runtime endpoint (default `http://localhost:8085`).

## Exercise Map

- Exercise-01-SatCom-Link-Overview
- Exercise-02-Link-Budget-Calculation
- Exercise-03-Frequency-Planning
- Exercise-04-VSAT-Network-Design
- Exercise-05-Link-Monitoring
- Exercise-06-Link-Troubleshooting
- Exercise-07-Beam-Footprint-Calculation
- Exercise-08-SatCom-Protocols-Practice
