# Exercise 08 - Scaling and Resilience

## Objective
Evaluate enterprise scaling constraints and design improvements for high-volume real-time monitoring deployments.

## Architecture
Current: single-node app + simple broker.
Target: multi-node app + broker relay + sticky sessions + regional partitioning.

## Steps
1. Identify bottlenecks in current in-memory broker design.
2. Propose topic partitioning strategy by region or network domain.
3. Define session affinity and reconnect behavior requirements.
4. Design fallback strategy for broker or node failures.
5. Outline observability SLOs for real-time delivery.

## Expected Output
- Written architecture proposal with failure scenarios.
- Clear migration path from simple broker to external relay.
- Backpressure and replay considerations documented.

## Solution
Adopt external message broker relay, add connection gateway controls, and implement resilient client reconnect + replay strategy.

## Learning Outcome
You can evolve a working prototype into a production-grade real-time monitoring architecture.
