# Day 12 - SNMP GET vs GETNEXT vs GETBULK

Day 12 turns SNMP request handling into a practical monitoring lab for NMS, telecom, SatCom, enterprise network, and NOC workflows. The code uses a deterministic in-memory MIB repository and reusable engines for GET, GETNEXT, GETBULK, hierarchy traversal, polling comparison, and dashboard aggregation.

## What You Will Learn
- How SNMP GET retrieves one exact managed object.
- How GETNEXT walks a MIB tree one lexicographic step at a time.
- How GETBULK reduces request count for larger datasets.
- How network operators use traversal and bulk polling for discovery and monitoring.
- How monitoring systems summarize routers, switches, modems, firewalls, and NOC status.

## Architecture
- `core/` contains the shared OID model, MIB repository, request/response types, engines, and monitoring services.
- Each `Exercise-XX-*` folder contains one runnable Java entrypoint.
- `diagrams/` documents the protocol flows with Mermaid.
- `scripts/run_all_tests.sh` compiles the module, runs all exercises, captures logs, and validates required markers.

## Exercises
| Exercise | Topic | Output Focus |
| --- | --- | --- |
| 01 | SNMP GET | Single OID retrieval, request, response, response time |
| 02 | SNMP GETNEXT | Sequential hierarchy traversal |
| 03 | SNMP GETBULK | Bulk system-group retrieval and request reduction |
| 04 | OID Discovery | Unknown-tree exploration with GETNEXT |
| 05 | Performance Comparison | GET vs GETNEXT vs GETBULK metrics |
| 06 | Network Monitoring | Router, switch, firewall polling |
| 07 | Telecom Monitoring | RF power, BER, carrier lock, frequency |
| 08 | NOC Dashboard | Multi-device operational overview |

## Quick Start
```bash
cd Day-12-SNMP-GET-GETNEXT-GETBULK
./scripts/run_all_tests.sh
```

## Notes
- Java 17 only.
- No external dependencies.
- Output is deterministic so the lab can be validated automatically.
