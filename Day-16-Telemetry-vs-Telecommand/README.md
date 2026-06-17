# Day 16 - Telemetry vs Telecommand

Day 16 teaches the control-plane difference between telemetry and telecommand using a Spring Boot 3.3.x / Java 17 REST API. Day 15 already covered monitoring, polling, traps, alerts, and dashboards. This module moves one step further: it shows how a control center uses telemetry to make operational decisions and then sends telecommands back to the device.

## Architecture

The module is a standalone Spring Boot application with H2 persistence and deterministic simulation services.

- `ManagedDevice` is the inventory of controlled assets.
- `TelemetryRecord` stores device-to-center measurements.
- `Telecommand` stores requested commands.
- `CommandResult` stores execution results and audit history.
- `Alarm` stores threshold violations and recovery state.
- `DeviceState` models operational transitions such as `ACTIVE`, `DEGRADED`, `RECOVERING`, and `RESTARTING`.

Telemetry flows from device to control center. Telecommand flows from control center to device. Closed-loop automation connects both paths by using telemetry to trigger recovery actions.

## Telemetry vs Telecommand

| Topic | Telemetry | Telecommand |
|---|---|---|
| Direction | Device -> Control Center | Control Center -> Device |
| Purpose | Observe state, health, and metrics | Change device behavior or state |
| Example | BER, RF power, CPU, temperature | Restart modem, enable carrier, switch backup link |
| Risk | Low operational risk | Higher operational risk, must validate state |
| Outcome | Visibility | Control |

## REST APIs

- `GET /api/devices`
- `GET /api/telemetry`
- `GET /api/alarms`
- `GET /api/commands`
- `POST /api/commands/restart/{deviceId}`
- `POST /api/commands/enable-interface/{deviceId}`
- `POST /api/commands/disable-interface/{deviceId}`
- `POST /api/commands/reset-modem/{deviceId}`
- `POST /api/commands/change-frequency/{deviceId}`
- `POST /api/commands/clear-alarm/{alarmId}`
- `GET /api/dashboard`

Example:

```bash
curl http://localhost:8082/api/devices
curl -X POST http://localhost:8082/api/commands/reset-modem/4
```

## Telecom Examples

A telecom router can receive telemetry showing an interface down or carrier instability. The control center can respond by disabling and re-enabling the interface, changing frequency, or enabling/disabling the carrier.

## SatCom Examples

A SatCom modem can report high BER or loss of carrier lock. The control center can reset the modem, switch to backup link, and verify recovery through fresh telemetry before clearing alarms.

## Real NOC Workflow

1. Telemetry shows a fault.
2. An alarm is generated.
3. The operator or automation engine chooses a corrective command.
4. The command is validated against device state.
5. The command executes and updates device state.
6. New telemetry confirms recovery.
7. The alarm is cleared and the audit trail is preserved.

## Running Instructions

```bash
cd Day-16-Telemetry-vs-Telecommand
mvn test
mvn spring-boot:run
```

Run the smoke harness:

```bash
chmod +x scripts/run_all_tests.sh
scripts/run_all_tests.sh
```

## Expected Outputs

- `TELEMETRY RECEIVED`
- `COMMAND EXECUTED`
- `STATE UPDATED`
- `TELECOM CONTROL`
- `SATCOM COMMAND`
- `REMOTE RECOVERY`
- `AUTO CORRECTION`
- `NOC CONTROL DASHBOARD`

## Learning Outcomes

- Distinguish telemetry from telecommand.
- Model stateful device control safely.
- Build control-center APIs that validate and audit commands.
- Use telemetry thresholds to drive recovery actions.
- Design deterministic, testable automation for telecom and SatCom operations.
