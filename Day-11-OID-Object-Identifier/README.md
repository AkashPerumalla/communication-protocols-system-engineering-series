# Day 11 - OID (Object Identifier)

Day 11 turns OIDs into a practical learning module for SNMP-based Network Management Systems, Telecom NOCs, Data Centers, and SatCom monitoring. The code is built around a reusable OID repository, deterministic lookup and traversal services, and realistic request-response simulations for GET, GETNEXT, and WALK.

## What You Will Learn
- How dotted OIDs map to named branches and managed objects.
- How hierarchy traversal works from `iso` to enterprise-specific telemetry.
- How SNMP GET, GETNEXT, and WALK behave in a simulator.
- How telecom and SatCom monitoring systems surface operational OIDs.
- How a NOC dashboard can aggregate device health and critical object status.

## Architecture
- `core/` contains the reusable OID model, tree, lookup, and simulation services.
- Each `Exercise-XX-*` folder contains one runnable Java entrypoint.
- `diagrams/` documents the flow with Mermaid diagrams.
- `scripts/run_all_tests.sh` compiles the module, runs all exercises, captures logs, and validates required markers.

## Exercises
| Exercise | Topic | Output Focus |
| --- | --- | --- |
| 01 | OID Basics | OID name, depth, parent, resolved path |
| 02 | OID Hierarchy | Tree traversal from `iso` to `system` |
| 03 | OID Lookup | Search by OID, name, and partial keyword |
| 04 | SNMP GET | Manager/agent GET request-response flow |
| 05 | SNMP WALK | Branch walk under the system group |
| 06 | Telecom OID Monitoring | RF power, BER, carrier lock, frequency |
| 07 | SatCom OID Monitoring | Eb/No, terminal state, modem status |
| 08 | NOC OID Dashboard | Multi-device operational summary |

## Quick Start
```bash
cd Day-11-OID-Object-Identifier
./scripts/run_all_tests.sh
```

## Notes
- Java 17 only.
- No external dependencies.
- Output is deterministic so the learning flow can be validated automatically.
