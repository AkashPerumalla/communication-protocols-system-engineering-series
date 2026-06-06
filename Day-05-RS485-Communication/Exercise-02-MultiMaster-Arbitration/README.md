Exercise 02b — Multi-Master Arbitration (Advanced)

Objective
- Demonstrate a simple token-based arbitration mechanism for multiple masters sharing an RS485-like bus (simulation). This shows how masters can avoid collisions by granting exclusive transmit rights.

Components
- `BusArbiter.java` — central arbiter that grants a token to connected masters in round-robin order.
- `TokenMasterA.java`, `TokenMasterB.java` — two masters that register with the arbiter and send poll commands when they hold the token.

Run
1. Start Device01/Device02/Device03 from `Exercise-02-MultiDevice-Network`.
2. Start the arbiter: `java BusArbiter` (port 8020).
3. Start both masters: `java TokenMasterA` and `java TokenMasterB`.

Expected
- Masters take turns (token passing) sending `READ_TEMP` to devices without collisions.
