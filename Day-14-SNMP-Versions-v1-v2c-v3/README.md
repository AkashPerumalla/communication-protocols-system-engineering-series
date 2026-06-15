# Day 14 - SNMP Versions v1, v2c, v3

Day 14 is a realistic SNMP simulation platform for understanding how version choice affects security, performance, and operational behavior in telecom and NOC environments. It is not a print-only comparison project. The exercises use a shared Java 17 core to simulate SNMPv1, SNMPv2c, and SNMPv3 request flows, community handling, USM authentication, privacy workflows, version comparison, telecom telemetry, secure NOC monitoring, and migration strategy.

## What You Will Learn
- SNMPv1 request and response flow
- SNMPv2c community-based communication
- SNMPv3 user-based security model
- Authentication and privacy workflows
- Security level tradeoffs
- Operational impact of protocol choice
- Telecom and SatCom monitoring patterns
- Secure NOC deployment practices
- Migration strategies from v1 to v2c to v3

## Architecture
The module follows the same shared-core, thin-exercise design used in Days 09-13.

- `core/` holds reusable simulation services and immutable models
- `Exercise-01` through `Exercise-08` each run one deterministic scenario
- `diagrams/` contains Mermaid workflows and architecture views
- `scripts/run_all_tests.sh` compiles everything with Java 17 and validates markers

## Core Models
- `SnmpVersion` - SNMPv1, SNMPv2c, SNMPv3
- `SecurityLevel` - `NO_AUTH_NO_PRIV`, `AUTH_NO_PRIV`, `AUTH_PRIV`
- `AuthenticationProtocol` - `NONE`, `MD5`, `SHA`
- `PrivacyProtocol` - `NONE`, `DES`, `AES`
- `SnmpRequest` and `SnmpResponse` - deterministic request/response models
- `SnmpAgent` and `SnmpManager` - simplified manager/agent simulation
- `AuthenticationEngine` and `EncryptionEngine` - security workflow simulators
- `VersionComparisonService`, `NocDashboardService`, `SampleData` - reusable scenario services

## Telemetry Baseline
The module includes deterministic telecom values for monitoring exercises:
- RF Power = -42 dBm
- BER = 1.2E-6
- Carrier Lock = LOCKED
- Frequency = 14.250 GHz
- Symbol Rate = 45 Msps

It also includes a SatCom profile:
- EbNo = 9.4 dB
- BUC Status = ONLINE
- LNB Status = ONLINE
- Modem Status = ONLINE
- Terminal State = OPERATIONAL

## Exercises
1. SNMPv1 Basics
2. SNMPv2c Communication
3. SNMPv3 Authentication
4. SNMPv3 Encryption
5. Version Comparison
6. Telecom Monitoring
7. Secure NOC Monitoring
8. Version Migration Simulator

## Quick Start
```bash
sh scripts/run_all_tests.sh
```

The script compiles the Java 17 sources, runs all eight exercises, stores logs in `captures/`, and validates the required markers.

## Validation Markers
- `SNMPv1 SUCCESS`
- `SNMPv2c SUCCESS`
- `AUTH SUCCESS`
- `ENCRYPTION ACTIVE`
- `VERSION COMPARISON`
- `TELECOM MONITORING`
- `SECURE NOC`
- `MIGRATION COMPLETE`
