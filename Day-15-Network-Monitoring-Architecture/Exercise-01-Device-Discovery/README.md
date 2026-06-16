# Exercise 01 - Device Discovery

## Objective
Discover the eight simulated network devices and understand the inventory layer that feeds the monitoring system.

## Architecture
- Device catalog in H2
- REST-backed device inventory
- Monitoring-friendly metadata such as hostname, IP address, category, and current health

## Steps
1. Start the Spring Boot application.
2. Call `GET /api/devices`.
3. Inspect the seeded inventory and identify the device roles.
4. Call `GET /api/devices/{id}` for a single device view.

## Expected Output
- Eight devices in the inventory
- Router, Switch, Modem, Hub, BUC, and LNB records available
- One offline device to represent a real operational fault

## Learning Outcome
You learn how a monitoring platform starts with authoritative device discovery before any polling, alerting, or dashboarding can happen.
