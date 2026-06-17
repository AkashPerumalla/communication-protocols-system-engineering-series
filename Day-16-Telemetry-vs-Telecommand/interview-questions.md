# Day 16 Interview Questions

1. What is telemetry in a control system?
   - Telemetry is data sent from a device to the control center.
   - It is used to observe health, performance, and fault conditions.
   - It does not directly change device behavior.

2. What is telecommand?
   - Telecommand is a command sent from the control center to a device.
   - It changes state, configuration, or operating mode.
   - It must be validated before execution.

3. Why is telecommand riskier than telemetry?
   - Commands can change device behavior immediately.
   - Bad commands can worsen faults or cause outages.
   - Validation and audit logging are mandatory.

4. How does a NOC use telemetry?
   - To detect thresholds, trends, and faults.
   - To drive alarms and operational decisions.
   - To confirm whether recovery actions worked.

5. How does telemetry drive telecommand decisions?
   - Fault telemetry creates an alarm.
   - The alarm policy determines the recovery action.
   - The recovery command is then executed and verified.

6. What is closed-loop automation?
   - It is the automatic cycle of observe, decide, act, and verify.
   - Telemetry triggers a command.
   - New telemetry confirms whether the action succeeded.

7. Why do we keep a command audit trail?
   - For operational accountability.
   - For troubleshooting and RCA.
   - For compliance and change management.

8. What makes a telecommand valid?
   - The device must support the requested action.
   - The device state must permit the action.
   - The command must be safe for the current operating mode.

9. How does the module represent device state?
   - With a dedicated `DeviceState` enum.
   - By persisting the latest state in `ManagedDevice`.
   - By recording state transitions in command results.

10. Why is H2 useful in this learning module?
    - It keeps the module self-contained.
    - It allows deterministic persistence and testing.
    - It avoids external infrastructure.

11. What is the difference between `RESTART_DEVICE` and `RESET_MODEM`?
    - A restart is a broader device recovery action.
    - A modem reset is narrower and SatCom-specific.
    - Both require state validation.

12. When would you use `ENABLE_INTERFACE`?
    - When an interface was intentionally disabled or recovered.
    - When the device supports interface control.
    - When a controlled state transition is acceptable.

13. When would you use `CHANGE_FREQUENCY`?
    - When telecom or SatCom operating frequency needs tuning.
    - When the device supports frequency control.
    - When the new frequency is within valid operating bounds.

14. What is the value of `CLEAR_ALARM`?
    - It closes the incident loop after recovery.
    - It records why the alarm was cleared.
    - It keeps the alarm history intact.

15. How do telecom and SatCom operations differ?
    - Telecom often focuses on interfaces, carriers, and synchronization.
    - SatCom emphasizes modem health, carrier lock, BER, and RF power.
    - Both rely on command validation and telemetry verification.

16. What telemetry metrics matter in telecom operations?
    - CPU, memory, temperature, power, and interface status.
    - RF power, carrier lock, frequency, and symbol rate.
    - Alarm-driven thresholds help identify degradation.

17. What telemetry metrics matter in SatCom operations?
    - EbNo, BER, uplink power, downlink power, and modem status.
    - BUC and LNB health.
    - Carrier lock and frequency stability.

18. What is the role of `AlarmGenerationService`?
    - It turns telemetry thresholds into active alarms.
    - It avoids duplicate alarms for the same device and metric.
    - It supports recovery and clearing.

19. What is the role of `CommandExecutionService`?
    - It validates and executes telecommands.
    - It updates device state and stores audit entries.
    - It drives telemetry verification after execution.

20. Why return DTOs from REST endpoints?
    - To avoid exposing persistence internals.
    - To keep APIs stable and intentional.
    - To separate read models from write models.

21. Why is a control center architecture stateful?
    - Device state affects whether a command is allowed.
    - Recovery history affects the next action.
    - Alarm state and command history must persist.

22. What is a backup link in this module?
    - It is an alternate operational path for recovery.
    - It supports `SWITCH_TO_BACKUP_LINK`.
    - It is useful when the primary path degrades.

23. How is `REMOTE RECOVERY` different from `AUTO CORRECTION`?
    - Remote recovery is a command-driven response to a fault.
    - Auto correction is a closed-loop automated response.
    - Both depend on telemetry and validation.

24. Why should command execution be deterministic in a learning module?
    - Deterministic behavior makes tests repeatable.
    - It helps learners correlate inputs with outputs.
    - It makes smoke validation reliable.

25. What does `TelemetryGeneratorService` do?
    - It creates deterministic telemetry records.
    - It simulates realistic telecom and SatCom metrics.
    - It triggers alarm evaluation after capture.

26. What is the purpose of `ManagedDevice`?
    - It is the authoritative inventory of controlled devices.
    - It stores current state and capability flags.
    - It anchors telemetry and command relationships.

27. What is the purpose of `CommandResult`?
    - It records the command outcome.
    - It stores previous and resulting state.
    - It provides an audit trail for operations.

28. Why include both telemetry and alarms in the dashboard?
    - Telemetry shows live operational context.
    - Alarms show unresolved faults.
    - Together they show how control decisions are made.

29. What should a dashboard emphasize in a NOC?
    - Device inventory.
    - Telemetry health.
    - Active alarms, recent commands, and recovery actions.

30. How should a telecommand API respond?
    - It should validate the request.
    - It should return the execution result and marker.
    - It should make the state transition observable.

31. Why use Java 17 here?
    - It is the target runtime for the series.
    - It supports modern language features and Spring Boot 3.x.
    - It is a common enterprise baseline.

32. What is the benefit of Spring Data JPA in this module?
    - It reduces boilerplate persistence code.
    - It makes stateful operations easier to test.
    - It integrates well with H2 for a self-contained lab.

33. How do you prevent duplicate alarms?
    - Check for an active alarm on the same device and metric.
    - Reuse the active alarm instead of creating a new one.
    - Only clear when recovery is verified.

34. What is a realistic SatCom recovery sequence?
    - Detect high BER or carrier loss.
    - Reset the modem or switch link path.
    - Verify carrier lock and then clear the alarm.

35. What is a realistic telecom recovery sequence?
    - Detect carrier or interface degradation.
    - Change frequency or enable/disable the carrier.
    - Confirm the interface is healthy again.

36. Why is state validation important before command execution?
    - It prevents invalid operations.
    - It protects the device from unsafe transitions.
    - It keeps the learning model realistic.

37. How do controllers stay thin in this architecture?
    - They delegate to services.
    - They map results to DTOs.
    - They avoid embedding business rules.

38. What is the operational meaning of `RECOVERING`?
    - The device is in a transitional state after a command.
    - Telemetry should be rechecked before declaring success.
    - Automation may still be running.

39. What does an audit trail contribute to RCA?
    - It shows what was commanded and when.
    - It shows which state changed.
    - It helps reconstruct failure and recovery steps.

40. How would you extend this module in production?
    - Add authenticated command workflows.
    - Add command approval and role-based access control.
    - Integrate with real device adapters and event brokers.

41. Why is `SYNC_CONFIGURATION` useful?
    - It represents operational reconciliation.
    - It models control-center to device configuration alignment.
    - It is common in telecom and industrial systems.

42. How do you test telecommand behavior?
    - Seed deterministic device data.
    - Execute commands against known device states.
    - Assert state transitions, audit entries, and markers.

43. Why separate alarm severity from alarm status?
    - Severity tells you impact.
    - Status tells you lifecycle.
    - The two are related but not the same.

44. What is the main lesson of Day 16?
    - Observability and control are different but connected.
    - Telemetry observes; telecommand acts.
    - The best operations systems use both safely.
