# Exercise 06 - SNMP Monitoring

## Objective
Simulate SNMP OID collection and map legacy protocol semantics into modern monitoring APIs.

## Architecture
- SNMPMonitoringService OID mapping layer
- OIDs for uptime, CPU, memory, interface in/out
- REST exposure via /api/snmp

## Implementation Steps
1. Collect latest metrics from storage.
2. Map fields to deterministic OID records.
3. Return polled-like responses through API.
4. Feed SNMP outputs into dashboard/reporting analysis.

## Expected Output
- SNMP MONITORING ACTIVE

## Solution Explanation
The exercise demonstrates how legacy SNMP signal models can be normalized and consumed by modern observability systems without replacing all existing infrastructure.
