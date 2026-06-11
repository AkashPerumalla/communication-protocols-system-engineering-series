# Day 10 - MIB (Management Information Base)

Day 10 continues the SNMP learning path from Day 08 and Day 09, but shifts the focus from protocol flow to the data model itself. The goal is to teach how a Management Information Base organizes network and telecom telemetry into a navigable tree of managed objects that operators, managers, and devices can all understand.

This day is simulator-first, console-based, Java 17 only, and dependency-free. It is designed for NOC engineers, network engineers, telecom engineers, SatCom engineers, embedded engineers, SNMP developers, and system engineers who need a practical understanding of how real monitoring platforms structure and consume MIB data.

## Learning Objectives
- Understand what a MIB is and why it exists.
- Read and explain OID hierarchy from `iso` to enterprise branches.
- Recognize MIB-II system objects and interface objects.
- Model vendor-specific enterprise MIBs.
- Browse MIB data by name, OID, parent, child, and branch.
- Interpret telecom and SatCom monitoring metrics in a MIB context.
- Build a centralized NOC-style dashboard from managed objects.
- Practice interview-ready SNMP and MIB terminology.

## Why This Day Exists
SNMP becomes meaningful only when you understand the data it exposes. Day 08 showed the protocol, and Day 09 showed manager versus agent behavior. Day 10 explains the information model behind those interactions.

If SNMP is the language, then the MIB is the dictionary, grammar, and directory structure combined.

## What Is a MIB?
A Management Information Base is a hierarchical catalog of managed objects.

Each object normally has:
- an OID
- a name
- a type
- an access mode
- a value

A MIB does not move packets by itself. It defines what an SNMP manager may ask for, what an agent may report, and how operators should interpret the data.

## Why MIB Exists
A MIB exists to solve these problems:
- Different vendors must expose device data in a consistent structure.
- Monitoring tools need predictable names and identifiers.
- Operators need a stable way to find important metrics.
- Automation systems need machine-readable object definitions.

Without a MIB, each device would invent its own naming scheme and monitoring tools would become vendor-specific and brittle.

## MIB Architecture
A MIB is usually understood as a tree.

The top-level branches define broad ownership domains. Deeper arcs narrow the scope from standards bodies to working groups to specific object types and finally to object instances.

The Day-10 lab models the tree in code using:
- `MibObject` for metadata and value content
- `MibNode` for tree nodes and navigation
- `MibTree` for lookup, traversal, and rendering
- `OidParser` for dotted OID parsing and comparison
- `EnterpriseMib` for vendor branches
- `MibRepository` for reusable access
- `SampleData` for deterministic demo values

## OID Hierarchy
OID hierarchy is central to MIB learning.

The Day-10 diagram [mib-hierarchy.md](diagrams/mib-hierarchy.md) illustrates the standard path:
- `iso(1)`
- `org(3)`
- `dod(6)`
- `internet(1)`
- `mgmt(2)`
- `mib-2(1)`

This path explains how standard management data is arranged before you reach object groups like system, interfaces, IP, TCP, UDP, and vendor extensions.

## MIB-II Overview
MIB-II is the standard reference point for network management.

It includes the common objects almost every engineer learns first:
- `sysDescr`
- `sysObjectID`
- `sysUpTime`
- `sysContact`
- `sysName`
- `sysLocation`
- `sysServices`

Day 10 Exercise 01 focuses on this group because it teaches scalar objects and gives a practical starting point for browsing and troubleshooting.

## Scalar Objects
Scalar objects represent a single value.

In SNMP, scalars are commonly addressed with the `.0` instance suffix.

Examples:
- `sysDescr.0`
- `sysName.0`
- `sysUpTime.0`

Scalar objects are a good way to introduce the idea that a MIB contains both structure and values.

## Enterprise MIB Overview
Enterprise MIBs extend the standard tree under `1.3.6.1.4.1`.

That branch is reserved for vendor-specific management information. In the Day-10 lab, the fictional Avantel branch uses:
- `1.3.6.1.4.1.99999`

That branch carries objects such as:
- `deviceTemperature`
- `rfPower`
- `ber`
- `carrierStatus`
- `terminalStatus`

These are realistic examples of how a vendor exposes equipment-specific visibility.

## Telecom Examples
Telecom monitoring uses MIB objects to track RF and service health.

Typical objects include:
- transmit power
- receive power
- frequency
- BER
- carrier lock
- alarm state

These values help operators detect signal degradation before service is lost.

## SatCom Examples
SatCom environments extend telecom monitoring with terminal-chain visibility.

Typical values include:
- BUC status
- LNB status
- signal level
- Eb/No
- terminal state

These values are critical for VSAT, hub, and remote terminal operations.

## NOC Examples
A NOC consumes MIB data from many systems at once.

Day-10 Exercise 08 combines:
- routers
- switches
- servers
- telecom terminals
- satellite terminals

The dashboard output gives a fast summary of online state, CPU, memory, temperature, and alarms.

## Module Structure

```text
Day-10-MIB-Management-Information-Base/
├── README.md
├── interview-questions.md
├── diagrams/
├── captures/
├── scripts/
├── poster/
├── core/
├── Exercise-01-MIB-II-System-Group/
├── Exercise-02-OID-Hierarchy-Explorer/
├── Exercise-03-MIB-Browser/
├── Exercise-04-Enterprise-MIB/
├── Exercise-05-Interface-Monitoring/
├── Exercise-06-Telecom-MIB-Monitoring/
├── Exercise-07-SatCom-MIB-Monitoring/
└── Exercise-08-NOC-MIB-Dashboard/
```

## Core Classes

### `MibObject`
Represents a managed object with OID, name, type, value, and access mode.

### `MibNode`
Represents a tree node and supports parent and child navigation.

### `MibTree`
Stores objects in hierarchy form and supports search, walk, and rendering.

### `OidParser`
Parses dotted OIDs and compares them lexicographically.

### `EnterpriseMib`
Provides vendor branch examples under the Avantel enterprise OID.

### `MibRepository`
Provides a single access point to the shared demo data.

### `SampleData`
Provides consistent object values for the exercises and validation script.

## Exercise Overview

### Exercise 01 - MIB-II System Group
Goal: Understand standard MIB-II system objects.

You will see `sysDescr`, `sysObjectID`, `sysUpTime`, `sysContact`, `sysName`, `sysLocation`, and `sysServices`.

The demo is intentionally simple because scalar objects should be easy to recognize.

### Exercise 02 - OID Hierarchy Explorer
Goal: Visualize OID tree structure.

You will walk from `iso(1)` through `mib-2(1)` and into real managed branches.

This makes the concept of tree navigation concrete instead of abstract.

### Exercise 03 - MIB Browser
Goal: Simulate a professional MIB browser.

You can search by OID, search by name, list children, show parent, and walk a branch.

This is the closest exercise to how operators actually navigate device telemetry.

### Exercise 04 - Enterprise MIB
Goal: Teach vendor-specific MIBs.

The Avantel branch shows how a vendor can expose thermal, RF, BER, and terminal status data.

This is a realistic pattern for telecom and SatCom platforms.

### Exercise 05 - Interface Monitoring
Goal: Monitor interfaces using IF-MIB concepts.

The demo tracks ifNumber, ifDescr, ifSpeed, ifInOctets, ifOutOctets, and ifOperStatus.

This is the standard starting point for link and port health.

### Exercise 06 - Telecom MIB Monitoring
Goal: Monitor telecom equipment.

The demo shows TX power, RX power, frequency, BER, carrier status, and alarm state.

These values reflect real-world service assurance operations.

### Exercise 07 - SatCom MIB Monitoring
Goal: Realistic satellite terminal monitoring.

The demo shows BUC status, LNB status, signal level, Eb/No, frequency, and terminal state.

This is the visibility model used in many ground-segment operations.

### Exercise 08 - NOC MIB Dashboard
Goal: Build a centralized monitoring dashboard.

The dashboard summarizes routers, switches, servers, telecom terminals, and satellite terminals.

It represents the kind of operational screen a NOC uses every day.

## Running Instructions

Compile and run everything with the automated script:

```bash
bash scripts/run_all_tests.sh
```

Run an individual exercise after compiling:

```bash
java -cp out/day10 SystemGroupExplorer
java -cp out/day10 OidHierarchyExplorer
java -cp out/day10 MibBrowserDemo
java -cp out/day10 EnterpriseMibDemo
java -cp out/day10 InterfaceMonitoringDemo
java -cp out/day10 TelecomMibMonitoringDemo
java -cp out/day10 SatComMibMonitoringDemo
java -cp out/day10 NocMibDashboard
```

## Build Model
The day uses the same direct compilation style as the earlier modules.

Important constraints:
- Java 17 only
- No external dependencies
- Default package
- Console output only
- Direct `javac`

## Validation Script
The orchestrator in [scripts/run_all_tests.sh](scripts/run_all_tests.sh) does four things:
- compiles all Java sources into `out/day10`
- runs each exercise
- stores logs in `captures/`
- validates required output markers

The required markers are:
- `sysDescr`
- `mib-2`
- `sysName`
- `deviceTemperature`
- `ifOperStatus`
- `BER`
- `Eb/No`
- `NOC DASHBOARD`

## Expected Outputs

### Exercise 01
- MIB-II system group output
- `sysDescr`
- `sysName`
- `sysLocation`

### Exercise 02
- OID hierarchy from `iso` to `mib-2`
- parent and child relationships
- tree traversal output

### Exercise 03
- search by name
- search by OID
- parent lookup
- child listing
- branch walk

### Exercise 04
- enterprise OID branch
- `deviceTemperature`
- `rfPower`
- `ber`

### Exercise 05
- `ifOperStatus`
- `eth0`
- `1000 Mbps`

### Exercise 06
- `BER`
- frequency
- carrier lock
- alarm state

### Exercise 07
- `Eb/No`
- `VSAT-01`
- `Signal: GOOD`

### Exercise 08
- `NOC DASHBOARD`
- ONLINE/OFFLINE rows
- CPU, memory, temperature, and alarm state columns

## Architecture Diagrams

- [mib-hierarchy.md](diagrams/mib-hierarchy.md)
- [mib-browser-workflow.md](diagrams/mib-browser-workflow.md)
- [mib-ii-system-group.md](diagrams/mib-ii-system-group.md)
- [enterprise-mib-architecture.md](diagrams/enterprise-mib-architecture.md)
- [telecom-monitoring-flow.md](diagrams/telecom-monitoring-flow.md)
- [satcom-monitoring-flow.md](diagrams/satcom-monitoring-flow.md)
- [noc-dashboard-architecture.md](diagrams/noc-dashboard-architecture.md)

Each diagram includes:
- explanation
- real-world relevance
- learning outcomes

## How to Think About the Data Model

The Day-10 data model is intentionally operational.

The exercises do not try to imitate a toy university lab. They mimic how real managed systems present visibility:
- identity
- health
- link state
- RF state
- alarms
- telemetry summaries

This is the level of abstraction used by NMS tools and device support teams.

## What the Exercises Teach Together

The eight exercises build up a complete learning arc.

1. Start with the system group.
2. Move to tree navigation.
3. Learn browser-style lookup.
4. Add vendor extensions.
5. Monitor interfaces.
6. Monitor telecom hardware.
7. Monitor SatCom terminals.
8. Consolidate into a NOC view.

That sequence mirrors how an engineer usually learns MIBs in the real world.

## Real-World Applications
- NOC monitoring for routers, switches, servers, and telecom gear.
- Vendor support workflows for private enterprise MIBs.
- SatCom operations for terminal and link visibility.
- Embedded systems where device telemetry must be exposed cleanly.
- SNMP development and automation.
- Troubleshooting where a browser is used to confirm object support.

## Role-Based Learning Paths

### For NOC Engineers
Focus on dashboards, alarms, interface status, and polling.

### For Network Engineers
Focus on MIB-II, interfaces, OID lookup, and branch traversal.

### For Telecom Engineers
Focus on BER, RF power, carrier lock, and alarm state.

### For SatCom Engineers
Focus on Eb/No, BUC/LNB state, signal quality, and terminal status.

### For Embedded Engineers
Focus on device identity, counters, and how firmware exposes telemetry.

### For SNMP Developers
Focus on OID structure, object modeling, access modes, and tree design.

### For System Engineers
Focus on cross-domain visibility and how MIBs support operations.

## Learning Outcomes
By the end of Day 10 you should be able to:
- explain what a MIB is
- trace a dotted OID
- identify MIB-II system objects
- distinguish standard and enterprise branches
- browse a MIB tree
- model telecom and SatCom telemetry
- describe a NOC dashboard built from managed objects

## Troubleshooting
- Use Java 17.
- Run the validation script from the Day-10 folder.
- Remove `out/day10` if you want a clean rebuild.
- Inspect `captures/` if a validation marker is missing.
- Re-run the script after changing any console output.

## Design Notes
The code intentionally stays small but realistic.

The design choices are:
- default package for direct compilation
- shared core for reuse
- deterministic sample data
- clear console formatting
- no external dependencies
- no hidden framework behavior

This makes the module easy to read, easy to run, and easy to extend.

## Output Style
The console output is intentionally formatted to look like engineering tooling rather than a classroom toy.

You will see:
- section headers
- OID and name pairs
- branch traversal
- status summaries
- NOC-like tables

This is what makes the day portfolio-quality.

## Capstone Summary
Day 10 turns SNMP from protocol mechanics into object modeling.

If Day 08 taught how to talk SNMP and Day 09 taught who talks to whom, Day 10 teaches what gets talked about.

That is the core of MIB understanding.

## Quick Reference OIDs

| OID | Name | Meaning |
| --- | --- | --- |
| 1.3.6.1.2.1.1.1.0 | sysDescr | System description |
| 1.3.6.1.2.1.1.2.0 | sysObjectID | Device identity |
| 1.3.6.1.2.1.1.3.0 | sysUpTime | Uptime |
| 1.3.6.1.2.1.1.4.0 | sysContact | Operator contact |
| 1.3.6.1.2.1.1.5.0 | sysName | Node name |
| 1.3.6.1.2.1.1.6.0 | sysLocation | Node location |
| 1.3.6.1.2.1.1.7.0 | sysServices | Service capability |
| 1.3.6.1.2.1.2.1.0 | ifNumber | Interface count |
| 1.3.6.1.2.1.2.2.1.2.1 | ifDescr | Interface description |
| 1.3.6.1.2.1.2.2.1.5.1 | ifSpeed | Interface speed |
| 1.3.6.1.2.1.2.2.1.8.1 | ifOperStatus | Interface state |
| 1.3.6.1.2.1.2.2.1.10.1 | ifInOctets | Inbound octets |
| 1.3.6.1.2.1.2.2.1.16.1 | ifOutOctets | Outbound octets |
| 1.3.6.1.4.1.99999.1.1 | deviceTemperature | Vendor thermal metric |
| 1.3.6.1.4.1.99999.1.2 | rfPower | Vendor RF metric |
| 1.3.6.1.4.1.99999.1.3 | ber | Vendor BER metric |
| 1.3.6.1.4.1.99999.1.4 | carrierStatus | Vendor carrier state |
| 1.3.6.1.4.1.99999.1.5 | terminalStatus | Vendor terminal state |
| 1.3.6.1.4.1.99999.2.1 | txPower | Telecom transmit power |
| 1.3.6.1.4.1.99999.2.2 | rxPower | Telecom receive power |
| 1.3.6.1.4.1.99999.2.3 | frequency | Telecom frequency |
| 1.3.6.1.4.1.99999.2.4 | ber | Telecom BER |
| 1.3.6.1.4.1.99999.2.5 | carrierStatus | Telecom carrier state |
| 1.3.6.1.4.1.99999.2.6 | alarmState | Telecom alarms |
| 1.3.6.1.4.1.99999.3.1 | bucStatus | SatCom BUC status |
| 1.3.6.1.4.1.99999.3.2 | lnbStatus | SatCom LNB status |
| 1.3.6.1.4.1.99999.3.3 | signalLevel | SatCom signal level |
| 1.3.6.1.4.1.99999.3.4 | ebNo | SatCom Eb/No |
| 1.3.6.1.4.1.99999.3.5 | frequency | SatCom frequency |
| 1.3.6.1.4.1.99999.3.6 | terminalState | SatCom terminal state |

## Final Takeaway
A MIB is not just a list of counters.

It is the operational model that lets a network, telecom, or satellite system explain itself to a manager.

Once you can read the tree, interpret the OIDs, and map them to real devices, SNMP becomes much easier to reason about.
