# Day 28 - Interview Questions: MCP Model Context Protocol

## Fundamentals

1. Question: What problem does MCP solve in enterprise AI systems?
   Answer: MCP standardizes tool and context exchange between clients and model-facing servers.
   Explanation: It avoids one-off integrations and enables reusable tool contracts.

2. Question: Why is stdio transport useful for MCP?
   Answer: It provides simple local process communication with minimal setup.
   Explanation: It is deterministic, portable, and suitable for secure local execution.

3. Question: What is a response envelope?
   Answer: A consistent API response schema containing metadata and payload.
   Explanation: It simplifies client handling, logging, and monitoring workflows.

4. Question: Why are marker constants important?
   Answer: They create deterministic, machine-verifiable status signals.
   Explanation: Test scripts and observability systems can validate behavior quickly.

5. Question: Why use constructor dependency injection in Python services?
   Answer: It separates object creation from usage and improves testability.
   Explanation: Dependencies can be replaced with mocks during unit testing.

6. Question: What does deterministic mode provide?
   Answer: Stable output values across repeated executions.
   Explanation: This prevents flaky tests and supports repeatable QA pipelines.

7. Question: Why use Pydantic DTOs for API inputs?
   Answer: They enforce type safety and validation.
   Explanation: Invalid requests fail early with structured error messages.

8. Question: Why maintain a tool registry?
   Answer: To centralize tool metadata and handlers.
   Explanation: Discovery, invocation, and access control become easier to manage.

9. Question: What is the role of global exception handling in FastAPI?
   Answer: It normalizes error responses and status codes.
   Explanation: Clients get predictable contract behavior even during failures.

10. Question: Why is path traversal protection required for file tools?
    Answer: It prevents unauthorized filesystem access.
    Explanation: Safe path resolution ensures reads remain inside allowed roots.

11. Question: Why separate API, services, and tools layers?
    Answer: To enforce single responsibility and maintainability.
    Explanation: Each layer can evolve independently with lower regression risk.

12. Question: What is the purpose of a health endpoint?
    Answer: It reports operational readiness for monitoring and orchestration.
    Explanation: Load balancers and scripts rely on it for service checks.

13. Question: Why keep PostgreSQL optional in a learning platform?
    Answer: It enables local portability without hard DB dependency.
    Explanation: A deterministic mock path supports complete offline validation.

14. Question: How does a platform status endpoint help operations teams?
    Answer: It aggregates system and integration health in one response.
    Explanation: NOC teams reduce troubleshooting time with centralized visibility.

## Intermediate

15. Question: How would you design MCP tool contracts for backward compatibility?
    Answer: Version arguments and return fields while preserving baseline schema.
    Explanation: Older clients continue to function while new fields are introduced.

16. Question: What are typical failure domains in MCP systems?
    Answer: Transport failures, tool runtime errors, and data-source outages.
    Explanation: Each domain needs isolated retries and explicit error mapping.

17. Question: Why should tool outputs be sorted deterministically?
    Answer: Sorting stabilizes snapshots and test assertions.
    Explanation: Non-deterministic ordering often causes false CI failures.

18. Question: How can response markers support SRE automation?
    Answer: Scripts can assert marker presence for pass/fail decisions.
    Explanation: This removes brittle text matching and improves reliability.

19. Question: What security controls are needed for REST API caller tools?
    Answer: URL allowlists, method restrictions, and timeout limits.
    Explanation: These controls reduce SSRF and denial-of-service exposure.

20. Question: Why separate domain datasets from service logic?
    Answer: It enables independent data refresh and deterministic seeding.
    Explanation: Logic remains stable while datasets evolve.

21. Question: How do you test MCP server/client integration effectively?
    Answer: Execute round-trip stdio exchanges with marker assertions.
    Explanation: Integration tests should validate both transport and tool behavior.

22. Question: What is the value of a query router in MCP platforms?
    Answer: It maps user intent to the most relevant tool.
    Explanation: This enables a unified query endpoint for operational use cases.

23. Question: Why include request timestamps in envelopes?
    Answer: They improve traceability and sequence reconstruction.
    Explanation: Incident analysis depends on temporal correlation.

24. Question: How can you prevent tool registry collisions?
    Answer: Enforce unique names and fail registration duplicates.
    Explanation: Collisions create ambiguous execution paths.

25. Question: What is the role of structured JSON logging?
    Answer: It standardizes event ingestion for log analytics platforms.
    Explanation: JSON logs are easier to parse, search, and correlate.

26. Question: Why test both positive and negative API paths?
    Answer: Production reliability depends on error path correctness.
    Explanation: Validation and exception behavior must be contract-safe.

27. Question: How should optional PostgreSQL connectivity be surfaced?
    Answer: Include explicit mode and database marker in status payload.
    Explanation: Operators can distinguish mock and real DB execution.

28. Question: Why expose a tools discovery endpoint?
    Answer: It supports self-describing clients and dynamic integrations.
    Explanation: Clients can adapt without hardcoded tool inventories.

29. Question: How do you design deterministic fallback data?
    Answer: Seed fixed records with stable IDs and timestamps.
    Explanation: Predictable baseline data supports reproducible tests.

30. Question: What should a run-all-tests script validate beyond pytest?
    Answer: Service startup, API markers, transport behavior, and pass summary.
    Explanation: End-to-end scripts validate operational readiness, not only logic.

31. Question: How do DI containers improve integration tests?
    Answer: They allow controlled replacement of settings and services.
    Explanation: Tests can isolate dependencies without monkeypatching internals.

32. Question: Why is bounded request timeout essential for tool callers?
    Answer: It prevents hanging threads and resource exhaustion.
    Explanation: Timeouts enforce predictable latency and resilience.

33. Question: What metrics are useful for NOC-facing MCP tools?
    Answer: Health status, alarm counts, latency, and query success ratios.
    Explanation: These metrics map directly to operational SLAs.

34. Question: How should global exceptions map to HTTP statuses?
    Answer: Validation to 400/422, not found to 404, dependency outage to 503.
    Explanation: Correct status codes improve client and incident handling.

## Advanced

35. Question: How can MCP platforms support multi-tenant isolation?
    Answer: Scope tool access by tenant context and policy checks.
    Explanation: Tenant isolation protects data boundaries and compliance.

36. Question: What tradeoff exists between deterministic mode and realism?
    Answer: Determinism improves testability but may hide runtime variability.
    Explanation: Systems should support deterministic testing and realistic production mode.

37. Question: How would you evolve this platform for high availability?
    Answer: Add horizontal API scaling, external state stores, and health-based routing.
    Explanation: Stateless service patterns simplify failover and scaling.

38. Question: How can MCP tool execution be audited for compliance?
    Answer: Log tool name, arguments hash, actor, and correlation ID.
    Explanation: Audit trails enable forensic and governance workflows.

39. Question: Why keep domain services independent from transport details?
    Answer: It prevents coupling to API/MCP protocol specifics.
    Explanation: Same services can be reused across CLI, API, and MCP channels.

40. Question: How can rate limiting be introduced safely?
    Answer: Apply middleware with route-aware budgets and exemptions.
    Explanation: Critical health endpoints may need stricter but separate policies.

41. Question: What is a robust strategy for SQL tool safety?
    Answer: Restrict SQL grammar, enforce read-only roles, and validate query classes.
    Explanation: Controlled query execution reduces data corruption risk.

42. Question: How should diagram-driven documentation aid onboarding?
    Answer: Show architecture, sequence, failure flow, and deployment topology.
    Explanation: Visual artifacts reduce ramp-up time for new engineers.

43. Question: How does envelope standardization simplify SDK design?
    Answer: SDK clients can implement one parser and marker dispatcher.
    Explanation: Uniform contracts reduce branching logic in client code.

44. Question: What is the key criterion for production-quality learning projects?
    Answer: They are runnable, testable, deterministic, and operationally observable.
    Explanation: Conceptual examples alone are insufficient for enterprise training.
