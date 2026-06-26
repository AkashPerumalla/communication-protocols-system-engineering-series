# Interview Questions - Docker Fundamentals and Spring Boot Containerization

1. Question: What problem does Docker solve in enterprise backend deployments?
   Answer: Docker provides consistent runtime environments across development, testing, and production.
   Explanation: It eliminates "works on my machine" issues by packaging app dependencies and runtime together.

2. Question: What is a Docker image?
   Answer: A Docker image is an immutable template used to create containers.
   Explanation: It contains filesystem layers, metadata, and startup instructions.

3. Question: What is a Docker container?
   Answer: A container is a running instance of an image.
   Explanation: Multiple containers can run from the same image with isolated resources.

4. Question: What is the difference between Dockerfile and Docker Compose?
   Answer: Dockerfile defines how to build one image, while Compose defines how multiple services run together.
   Explanation: Compose handles networking, volumes, environment variables, and dependencies.

5. Question: Why use multi-stage builds?
   Answer: Multi-stage builds reduce final image size and remove build-time dependencies.
   Explanation: Builder tools stay in intermediate stages, runtime stage remains minimal.

6. Question: What is the purpose of .dockerignore?
   Answer: It excludes unnecessary files from build context.
   Explanation: Smaller context improves build performance and avoids leaking local artifacts.

7. Question: Why should application configuration be externalized?
   Answer: Externalization allows different environments to reuse the same image.
   Explanation: Variables like DB URL, credentials, and ports are provided at runtime.

8. Question: Why is Docker layer caching important?
   Answer: Layer caching speeds up incremental builds.
   Explanation: Stable layers like dependencies are reused if unchanged.

9. Question: Why split COPY pom.xml and COPY src in Java Dockerfiles?
   Answer: It maximizes cache reuse for Maven dependency resolution.
   Explanation: Source changes do not invalidate dependency layers.

10. Question: What is the benefit of using eclipse-temurin:17-jre in runtime stage?
    Answer: It provides a smaller runtime footprint than a full JDK.
    Explanation: Production images should include only runtime requirements.

11. Question: What is a Docker volume?
    Answer: A Docker volume is persistent storage managed by Docker.
    Explanation: It preserves data across container recreation.

12. Question: Why should MySQL use a named volume in Compose?
    Answer: It ensures database state survives restarts and upgrades.
    Explanation: Stateful enterprise workloads require persistent storage.

13. Question: What is bridge networking in Docker Compose?
    Answer: Bridge networking creates an isolated virtual network for services.
    Explanation: Services communicate by service names like mysql and spring-app.

14. Question: How does service discovery work in Compose?
    Answer: Docker DNS resolves service names to container IPs automatically.
    Explanation: Applications use jdbc:mysql://mysql:3306/... rather than static IPs.

15. Question: What is a health check in Docker?
    Answer: A health check is a command used to evaluate container health status.
    Explanation: Orchestrators and scripts use health state for readiness decisions.

16. Question: Why is depends_on: condition: service_healthy useful?
    Answer: It prevents dependent services from starting before dependencies are ready.
    Explanation: It reduces startup race conditions such as DB connection failures.

17. Question: What is the difference between liveness and readiness checks?
    Answer: Liveness checks verify process health; readiness checks verify traffic readiness.
    Explanation: A service may be alive but not ready to serve requests.

18. Question: Why expose /actuator/health in Spring Boot containers?
    Answer: It provides a standard readiness endpoint for automation and operations.
    Explanation: Compose and external monitors can use it for health validation.

19. Question: Why keep logs on stdout/stderr in containers?
    Answer: Container platforms collect stdout logs centrally.
    Explanation: File logging complicates log shipping and storage management.

20. Question: What is graceful shutdown in Spring Boot?
    Answer: It allows in-flight requests to complete before process termination.
    Explanation: It improves reliability during rolling restarts and maintenance windows.

21. Question: Why use profiles like default and docker?
    Answer: Profiles separate environment-specific settings without code changes.
    Explanation: Example: H2 for local development, MySQL for containerized runtime.

22. Question: Why use H2 in development and MySQL in docker profile?
    Answer: H2 enables fast local runs while MySQL simulates production behavior.
    Explanation: This balances developer speed and realistic deployment validation.

23. Question: What are common production risks when containerizing Spring Boot?
    Answer: Large image sizes, hardcoded configs, missing health checks, and poor startup sequencing.
    Explanation: These issues cause slow deployments and unstable runtime behavior.

24. Question: Why pin image tags (for example mysql:8.4)?
    Answer: Pinned tags improve reproducibility and reduce drift.
    Explanation: Latest tags can introduce breaking changes unexpectedly.

25. Question: How do you inspect running container logs?
    Answer: Use docker logs or docker compose logs.
    Explanation: These commands provide centralized process output for debugging.

26. Question: How do you enter a running container for troubleshooting?
    Answer: Use docker exec -it <container> sh.
    Explanation: It opens an interactive shell in the container namespace.

27. Question: What is the difference between docker stop and docker kill?
    Answer: stop sends SIGTERM then SIGKILL after timeout, kill sends SIGKILL immediately.
    Explanation: stop is preferred for graceful application termination.

28. Question: Why avoid baking secrets directly into images?
    Answer: Embedded secrets are hard to rotate and easy to leak.
    Explanation: Use environment variables or secret managers at runtime.

29. Question: What does docker compose down -v do?
    Answer: It removes containers, networks, and named volumes created by the compose project.
    Explanation: Useful for full environment reset in testing.

30. Question: What does docker compose up -d --build do?
    Answer: It rebuilds images if needed and starts services in detached mode.
    Explanation: Common workflow for iterative development.

31. Question: How can you optimize Java container startup memory behavior?
    Answer: Configure JVM container flags like MaxRAMPercentage.
    Explanation: JVM should be tuned to container memory constraints.

32. Question: Why is deterministic seed data useful in learning and QA environments?
    Answer: It provides predictable test baselines.
    Explanation: Consistent counts and values simplify smoke and integration validation.

33. Question: How does Spring Data JPA help in containerized backends?
    Answer: It abstracts persistence logic and reduces boilerplate repository code.
    Explanation: Faster development with clear data access contracts.

34. Question: What should a production Dockerfile avoid?
    Answer: Installing unnecessary packages, running as root when avoidable, and copying unused files.
    Explanation: Minimization improves security and efficiency.

35. Question: How do you verify container health from CLI?
    Answer: Use docker inspect on .State.Health.Status or hit actuator endpoints.
    Explanation: Health status transitions from starting to healthy.

36. Question: Why should automation scripts trap cleanup?
    Answer: Cleanup traps prevent orphaned containers on script failure.
    Explanation: Reliable teardown avoids polluted test environments.

37. Question: How do environment variables support twelve-factor apps?
    Answer: They separate config from code.
    Explanation: The same artifact can run in multiple environments with runtime configuration.

38. Question: What is image immutability and why does it matter?
    Answer: Images should not change after build.
    Explanation: Immutability enables traceable, repeatable deployments.

39. Question: Why are integration tests still important when using containers?
    Answer: Containerization does not validate business logic correctness.
    Explanation: API contracts, seeding, and transaction behavior still need tests.

40. Question: What is the difference between image optimization and runtime optimization?
    Answer: Image optimization reduces build artifact size; runtime optimization tunes execution behavior.
    Explanation: Both are needed for production performance.

41. Question: What is the purpose of EXPOSE in Dockerfile?
    Answer: It documents the container port intended for runtime traffic.
    Explanation: EXPOSE does not publish ports; runtime mapping handles that.

42. Question: Why should container startup be idempotent?
    Answer: Restarts and rescheduling are common in production.
    Explanation: Startup logic should handle repeated execution safely.

43. Question: How does actuator /info help operations teams?
    Answer: It provides deployment metadata such as version and domain context.
    Explanation: Useful for runbooks and observability dashboards.

44. Question: Why is a dedicated docker profile better than hardcoding docker settings in default profile?
    Answer: It keeps local and container concerns isolated.
    Explanation: Prevents accidental coupling and simplifies environment overrides.

45. Question: How do you handle DB startup delay in Compose-based workflows?
    Answer: Use DB health checks and startup wait loops in automation.
    Explanation: Prevents transient connection failures during app boot.

46. Question: What is an enterprise use case for this Day-25 platform?
    Answer: NOC monitoring backend aggregating telemetry, alarms, and operational reports.
    Explanation: Mirrors telecom control-plane observability patterns.

47. Question: Why validate response markers in smoke tests?
    Answer: Marker assertions verify both endpoint availability and functional semantics.
    Explanation: It catches regressions that plain HTTP 200 checks may miss.

48. Question: What are common MySQL container hardening practices?
    Answer: Use strong credentials, constrained ports, backups, and version pinning.
    Explanation: Stateful data services require stricter operational controls.

49. Question: How would you evolve this setup for production orchestration platforms?
    Answer: Add Kubernetes manifests, probes, secrets, and autoscaling policies.
    Explanation: Compose is excellent for local and staging simulation, not full cluster orchestration.

50. Question: What key production-ready traits are demonstrated in this project?
    Answer: Multi-stage builds, health checks, profile config, env externalization, deterministic tests, and automated smoke validation.
    Explanation: These are core practices for reliable enterprise container deployments.
