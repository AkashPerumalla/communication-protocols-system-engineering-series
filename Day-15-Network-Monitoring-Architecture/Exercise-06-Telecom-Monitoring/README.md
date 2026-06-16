# Exercise 06 - Telecom Monitoring

## Objective
Model a telecom hub with RF metrics that resemble a carrier-grade NOC or satellite ground station.

## Architecture
- Hub-01 specialization
- RF Power, BER, Carrier Lock, Frequency, Symbol Rate
- Shared monitoring metric persistence

## Steps
1. Start the application.
2. Review `GET /api/metrics`.
3. Locate the Hub-01 record.
4. Inspect telecom-specific fields.

## Expected Output
- RF Power around -42 dBm
- BER near 1.2E-6
- Carrier lock reported as LOCKED

## Learning Outcome
You learn how telecom monitoring adds domain-specific measurements on top of a standard monitoring pipeline.
