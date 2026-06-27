# Day 26 - Kubernetes Basics

Day-26 turns the device monitoring platform into a Kubernetes-native learning project for telecom NOCs, SatCom backends, and IoT fleet operations. The project keeps the series' deterministic Spring Boot simulation style, but adds realistic deployment lifecycle handling, cluster health modelling, resource governance, ConfigMap and Secret wiring, and local-cluster automation for Docker Desktop Kubernetes.

## Learning Objectives
- Understand how a Spring Boot monitoring service is packaged for Kubernetes.
- Model deployment rollouts, scaling, rollback, and cluster readiness in application code.
- Use ConfigMaps, Secrets, Services, Ingress, PVCs, quotas, limits, HPAs, and NetworkPolicies.
- Expose liveness, readiness, info, and Prometheus-ready metrics using Actuator.
- Build deterministic seed data and automated tests that work in both H2 and MySQL-backed flows.
- Validate a local enterprise-style deployment pipeline with Docker and kubectl.

## Technology Stack
- Java 17
- Spring Boot 3.3.2
- Spring Web, Spring Data JPA, Validation, Actuator
- H2 for local deterministic tests
- MySQL for Docker and Kubernetes runtime
- Docker multi-stage build with non-root runtime
- Kubernetes manifests for Docker Desktop Kubernetes

## Project Layout
- `src/main/java/com/sky2dev/day26/config` - scheduling configuration
- `src/main/java/com/sky2dev/day26/constants` - API response markers
- `src/main/java/com/sky2dev/day26/controller` - REST endpoints for platform, devices, telemetry, alarms, notifications, deployments, dashboard, and cluster health
- `src/main/java/com/sky2dev/day26/dto` - ApiResponse and transport contracts
- `src/main/java/com/sky2dev/day26/entity` - JPA entities for device, telemetry, alarm, notification, deployment, and cluster health state
- `src/main/java/com/sky2dev/day26/health` - Actuator-integrated platform health indicator
- `src/main/java/com/sky2dev/day26/mapper` - response mappers
- `src/main/java/com/sky2dev/day26/repository` - JPA repositories and query methods
- `src/main/java/com/sky2dev/day26/scheduler` - background simulation jobs
- `src/main/java/com/sky2dev/day26/seed` - deterministic bootstrap data
- `src/main/java/com/sky2dev/day26/service` - business services
- `src/test/java/com/sky2dev/day26` - unit, repository, integration, health, and seed-data tests
- `k8s/` - namespace, config, storage, workload, networking, autoscaling, and governance manifests
- `scripts/run_all_tests.sh` - Maven, Docker, Kubernetes validation pipeline

## Domain Model
- Device: monitored telecom, SatCom, or IoT infrastructure element.
- TelemetryRecord: deterministic latency and signal-quality metrics.
- AlarmEvent: threshold or availability breach requiring NOC action.
- NotificationEvent: downstream delivery to email, SMS, webhook, or console.
- DeploymentStatus: rollout-aware deployment lifecycle view.
- ClusterHealth: aggregated namespace-level health summary.

## API Surface
- `GET /api/platform/status`
- `GET /api/devices`
- `GET /api/telemetry`
- `GET /api/alarms`
- `GET /api/dashboard`
- `GET /api/notifications`
- `GET /api/deployments`
- `GET /api/cluster/health`
- `POST /api/deployment/scale/{replicas}`
- `POST /api/deployment/rolling-update`
- `POST /api/deployment/rollback`
- `GET /actuator/health`
- `GET /actuator/info`

## Kubernetes Assets
- `namespace.yaml` - workload isolation
- `resource-quota.yaml` and `limit-range.yaml` - resource governance
- `configmap.yaml` and `secret.yaml` - externalized runtime configuration
- `persistent-volume.yaml` and `persistent-volume-claim.yaml` - MySQL persistence
- `mysql-deployment.yaml` - backing database workload
- `deployment.yaml` - application deployment with probes and rolling updates
- `service.yaml` - ClusterIP for MySQL and NodePort for the app
- `ingress.yaml` - HTTP entry point
- `hpa.yaml` - autoscaling policy
- `network-policy.yaml` - database access restriction

## Running Instructions
```bash
mvn clean test
./scripts/run_all_tests.sh
```

Default app port is `8091`. The NodePort service exposes the application on `30091`, and the recommended Ingress host is `day26.local`.

## Exercises
1. Exercise 01 - Kubernetes namespaces and configuration wiring.
2. Exercise 02 - Deployment rollout and scaling control.
3. Exercise 03 - Service discovery and stable connectivity.
4. Exercise 04 - ConfigMap and Secret externalization.
5. Exercise 05 - Probes and cluster-health evaluation.
6. Exercise 06 - Autoscaling and resource governance.
7. Exercise 07 - Persistent storage and network isolation.
8. Exercise 08 - End-to-end validation pipeline.

## Diagrams
Seven Mermaid diagrams are provided in `diagrams/` covering architecture, rollout flow, health monitoring, configuration flow, and observability.

## Interview Preparation
See `interview-questions.md` for a 50-question enterprise-oriented review set.

## Key Takeaways
- Kubernetes is not only a deployment target; it changes configuration, health, rollout, and recovery design.
- Deterministic learning projects can still model realistic cluster behaviour.
- Actuator, probes, ConfigMaps, Secrets, and rollout-aware services form the minimum operational baseline for production-style Spring Boot workloads.
