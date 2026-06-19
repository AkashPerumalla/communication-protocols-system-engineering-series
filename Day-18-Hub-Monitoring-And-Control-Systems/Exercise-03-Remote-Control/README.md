# Exercise 03: Remote Device Control

Objective: Execute operational control commands from NOC and validate audit-trail persistence.

Steps:
1. Reboot modem with `POST /api/control/reboot/{id}`.
2. Reset interface with `POST /api/control/reset-interface/{id}`.
3. Enable or disable a port.
4. Push config changes via `POST /api/control/change-config/{id}`.

Expected marker: CONTROL EXECUTED
