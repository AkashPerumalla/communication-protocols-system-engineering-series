# Exercise 07 - SatCom Monitoring

## Objective
Model a satellite communication modem and its dependent ground-segment telemetry.

## Architecture
- Modem-01 specialization
- EbNo, modem status, BUC status, LNB status, uplink and downlink power
- Stored in the shared monitoring metric table

## Steps
1. Start the application.
2. Review `GET /api/metrics`.
3. Locate the Modem-01 telemetry row.
4. Observe the SatCom-specific fields.

## Expected Output
- EbNo displayed in a realistic operational range
- Modem, BUC, and LNB status values available in one response

## Learning Outcome
You learn how SatCom monitoring combines terrestrial device health with radio-frequency path visibility.
