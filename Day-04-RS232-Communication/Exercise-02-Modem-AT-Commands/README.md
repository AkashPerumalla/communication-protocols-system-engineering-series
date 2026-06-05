# Exercise 02 — Modem AT Commands

Objective

Learn the Hayes AT command architecture and practice common modem commands.

Files

- `ATCommandSimulator.java` — simple Hayes/AT simulator responding to `AT`, `ATI`, `AT+CSQ`, `AT+RST`.
- `ATCommandClient.java` — interactive client that measures round-trip time and shows raw responses.

Run

```bash
cd Day-04-RS232-Communication/Exercise-02-Modem-AT-Commands
javac ATCommandSimulator.java ATCommandClient.java
# Terminal A
java ATCommandSimulator 7002
# Terminal B
java ATCommandClient localhost 7002
```

Expected sequence

```
AT
OK

ATI
MODEM v1.0

AT+CSQ
SIGNAL=78

AT+RST
REBOOTING...
OK
```

Notes

- This exercise illustrates the Hayes command set and simple modem interactions. Real GSM modems extend the AT set with vendor-specific commands and asynchronous URCs (unsolicited result codes).
- For hardware modems, use a serial library and open the appropriate COM/TTY device at 115200 or modem default baud.
