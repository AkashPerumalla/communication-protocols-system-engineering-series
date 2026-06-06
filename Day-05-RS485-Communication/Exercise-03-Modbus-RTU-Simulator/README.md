Exercise 03 — Modbus RTU Simulator

Objective
- Simulate Modbus RTU Function Code 03 (Read Holding Registers) with CRC16 validation.

Example
- Request (hex): `01 03 00 00 00 02 CRC_LO CRC_HI`
- Response (hex): `01 03 04 00 64 00 32 CRC_LO CRC_HI` (registers 0x0064=100 and 0x0032=50)

Run
- Compile and start `ModbusSlave` then run `ModbusMaster` to send request and decode registers.
