# Interview Questions - Kubernetes Basics

## 1. What is Kubernetes?
Answer: Kubernetes is an orchestration platform for deploying, scaling, and operating containerized workloads.
Explanation: It manages desired state through declarative objects such as Deployments, Services, ConfigMaps, and Ingress resources.

## 2. What problem does a Pod solve?
Answer: A Pod groups one or more tightly coupled containers into the smallest deployable unit.
Explanation: Containers in the same Pod share networking and storage, which makes sidecar and helper patterns possible.

## 3. What is a Deployment?
Answer: A Deployment manages replica creation and rollout of Pods.
Explanation: It adds declarative updates, rollback support, and replica control on top of ReplicaSets.

## 4. Why is a Service needed?
Answer: A Service gives Pods a stable network identity.
Explanation: Pod IPs are ephemeral, so Services provide a durable virtual IP and DNS name for access.

## 5. What is the difference between ClusterIP and NodePort?
Answer: ClusterIP is internal-only, while NodePort exposes a Service on each node.
Explanation: ClusterIP is used for in-cluster communication and NodePort is commonly used for local lab access.

## 6. What is an Ingress?
Answer: Ingress is an HTTP routing object for external traffic entering the cluster.
Explanation: It maps hostnames and paths to Services and usually depends on an Ingress controller.

## 7. What is a ConfigMap?
Answer: A ConfigMap stores non-sensitive configuration for workloads.
Explanation: It is used to inject settings like profiles, hostnames, ports, and feature flags without rebuilding images.

## 8. What is a Secret?
Answer: A Secret stores sensitive configuration such as credentials or tokens.
Explanation: It can be mounted as environment variables or files and should replace hard-coded secrets in manifests.

## 9. Why are readiness and liveness probes different?
Answer: Readiness decides if a Pod should receive traffic and liveness decides if it should be restarted.
Explanation: A process can be alive but not ready, especially during startup, dependency outages, or rollout transitions.

## 10. What is a startup probe?
Answer: A startup probe protects slow-starting applications from premature liveness failures.
Explanation: It delays liveness and readiness enforcement until the application has had enough time to bootstrap.

## 11. What is an HPA?
Answer: An HPA automatically changes replica counts based on metrics.
Explanation: CPU and memory utilization are common inputs, but custom metrics can also drive scaling.

## 12. Why do requests and limits matter?
Answer: Requests influence scheduling and limits cap resource consumption.
Explanation: Without them, workloads can be over-scheduled or trigger noisy-neighbour problems and OOM kills.

## 13. What is a PersistentVolume?
Answer: A PersistentVolume is a cluster resource representing durable storage.
Explanation: It abstracts the physical backing storage from the application that consumes it.

## 14. What is a PersistentVolumeClaim?
Answer: A PersistentVolumeClaim is a request for storage by a workload.
Explanation: It decouples application manifests from concrete storage implementation details.

## 15. Why is MySQL usually stateful in Kubernetes?
Answer: MySQL requires durable data across Pod restarts and re-scheduling.
Explanation: That persistence requirement makes PVs and PVCs central to reliable database deployment.

## 16. What is a Namespace?
Answer: A Namespace isolates workloads logically inside a cluster.
Explanation: It provides a scope for names, quotas, policies, and team or environment separation.

## 17. What does a ResourceQuota do?
Answer: A ResourceQuota caps resource usage inside a Namespace.
Explanation: It prevents one workload set from consuming all Pods, memory, CPU, or PVC allocations.

## 18. What is a LimitRange?
Answer: A LimitRange defines default or maximum resource values for objects in a Namespace.
Explanation: It helps enforce baseline resource discipline even when manifests omit requests and limits.

## 19. What is a NetworkPolicy?
Answer: A NetworkPolicy restricts Pod-to-Pod network communication.
Explanation: It implements least-privilege networking so that only approved traffic paths remain open.

## 20. Why is imagePullPolicy important in local clusters?
Answer: It controls whether Kubernetes reuses a local image or pulls from a registry.
Explanation: In Docker Desktop Kubernetes, `IfNotPresent` is usually appropriate for local image builds.

## 21. What is a rolling update?
Answer: A rolling update replaces Pods gradually instead of stopping everything at once.
Explanation: It reduces downtime and allows traffic to continue flowing through healthy replicas during change.

## 22. What is rollback in a Deployment?
Answer: Rollback returns a workload to a previous known-good version.
Explanation: It is essential when new images or configuration changes degrade service.

## 23. Why is graceful shutdown important for Spring Boot in Kubernetes?
Answer: Graceful shutdown allows in-flight requests to complete before termination.
Explanation: Combined with readiness changes and preStop hooks, it prevents dropped traffic during rollouts.

## 24. How does Spring Boot Actuator help on Kubernetes?
Answer: Actuator exposes operational endpoints for health, info, and metrics.
Explanation: These endpoints are used by probes, monitoring systems, and automation scripts.

## 25. Why expose Prometheus metrics?
Answer: Prometheus metrics provide scrape-friendly observability for applications and platforms.
Explanation: They allow dashboards, alerts, and scaling policies to be driven by real runtime behaviour.

## 26. What is the purpose of deterministic seed data in a learning project?
Answer: Deterministic seed data keeps tests and demos reproducible.
Explanation: Exact counts and stable values prevent flaky tests and make architecture behaviour easier to reason about.

## 27. Why is constructor injection preferred in Spring services?
Answer: Constructor injection makes dependencies explicit and easier to test.
Explanation: It improves immutability and avoids hidden runtime wiring issues.

## 28. What is layered architecture in this Day-26 project?
Answer: Layered architecture separates controllers, services, repositories, entities, DTOs, and mappers.
Explanation: That separation keeps transport, business logic, and persistence concerns from bleeding into each other.

## 29. Why use DTOs instead of returning entities directly?
Answer: DTOs protect the API contract from persistence details.
Explanation: They allow controlled response shaping and prevent accidental exposure of internal fields.

## 30. What is the purpose of ApiResponse in this project?
Answer: ApiResponse standardizes success state, marker, message, data, and timestamp.
Explanation: It makes automation, smoke tests, and operational tooling consistent across endpoints.

## 31. What is a marker string in this series?
Answer: A marker string is a deterministic response token used for validation.
Explanation: Shell scripts and tests grep or assert those markers to confirm the intended workflow executed.

## 32. Why does this project model deployment state inside the application?
Answer: It teaches rollout, scale, and rollback logic without requiring a production control-plane integration.
Explanation: Learners can understand operational flows while still working in a deterministic local lab.

## 33. What does cluster health aggregation mean?
Answer: Cluster health aggregation turns multiple signals into one operational view.
Explanation: Node readiness, Pod state, storage status, Service count, and latency indicators are combined into a summary.

## 34. Why would alarms be linked to telemetry records?
Answer: Telemetry provides the observed evidence that triggers an alarm.
Explanation: This linkage makes threshold violations traceable and useful for NOC triage.

## 35. Why would notifications link to alarms?
Answer: Notifications carry alarm context to downstream operators or systems.
Explanation: That relationship preserves incident lineage from measurement to alert delivery.

## 36. What is the difference between desired and available replicas?
Answer: Desired replicas are the target count and available replicas are the currently healthy count.
Explanation: The gap between them shows whether scaling or rollout is still converging.

## 37. Why is a non-root container runtime recommended?
Answer: Running as non-root reduces the blast radius of container compromise.
Explanation: It is a baseline hardening step in enterprise container platforms.

## 38. Why is open-in-view usually disabled in production-style Spring apps?
Answer: Disabling open-in-view avoids lazy loading during view rendering.
Explanation: It encourages clear transactional boundaries and prevents hidden database access patterns.

## 39. Why are tests separated into unit, repository, integration, and health categories?
Answer: Each test type validates a different architectural risk.
Explanation: Unit tests check logic, repository tests check persistence, integration tests check HTTP contracts, and health tests check operations.

## 40. What does `kubectl apply --dry-run=client` validate?
Answer: It validates manifest structure and client-side schema without changing the cluster.
Explanation: It is useful as a fast pre-flight check in local automation.

## 41. Why is `kubectl rollout status` used in automation?
Answer: It blocks until a Deployment converges or times out.
Explanation: That makes follow-up API checks meaningful because they happen after the workload is ready.

## 42. Why might port-forwarding be used in a local test script?
Answer: Port-forwarding provides a stable local access path to a Service.
Explanation: It avoids external DNS or Ingress dependencies during smoke testing.

## 43. What does `imagePullPolicy: IfNotPresent` imply?
Answer: The node reuses a local image if it already exists.
Explanation: That is useful in local Docker Desktop workflows where images are built on the same daemon.

## 44. Why are ConfigMap and Secret environment variables preferable to hard-coded values?
Answer: They separate deployment-time configuration from build-time artifacts.
Explanation: This improves portability across local, staging, and production-style environments.

## 45. Why should a health endpoint not be the same as business readiness?
Answer: Process health and domain readiness are related but not identical.
Explanation: An app can be alive technically while still not ready to serve useful business traffic.

## 46. What operational role does Ingress play in enterprise clusters?
Answer: Ingress centralizes HTTP routing, hostname mapping, and often TLS termination.
Explanation: It keeps edge access policy separate from individual application Deployments.

## 47. What is the main risk of missing resource limits?
Answer: One workload can consume excessive resources and destabilize other workloads on the node.
Explanation: Limits are a control mechanism, not just a documentation detail.

## 48. Why is rollback automation important in telecom or SatCom systems?
Answer: Fault recovery time directly affects service availability and operational SLA.
Explanation: Fast rollback reduces outage duration when configuration or rollout changes fail.

## 49. Why is this Day-26 project not just a CRUD app?
Answer: It models operational behaviours such as scaling, probes, alerts, notifications, and cluster state.
Explanation: Those behaviours are closer to real NOC workloads than simple create-read-update-delete exercises.

## 50. What is the biggest learning outcome from combining Spring Boot with Kubernetes here?
Answer: Application design and platform design have to support each other.
Explanation: Packaging, health, configuration, persistence, rollout control, and observability are part of the same system boundary.
