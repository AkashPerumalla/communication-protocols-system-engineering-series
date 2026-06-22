# Day-21 REST API Interview Questions

## Beginner

1. What is REST and why is it widely used in backend systems?
Answer: REST is an architectural style that models resources over HTTP using standard methods and stateless interactions. It is widely used because it is simple, interoperable, and well suited for distributed systems.

2. What is a resource in a REST API?
Answer: A resource is a domain object or collection identified by a URI, such as a device, metric, or audit log.

3. Why do REST APIs commonly use HTTP methods like GET, POST, PUT, and DELETE?
Answer: These methods provide a standardized contract for retrieving, creating, updating, and deleting resources.

4. What is the difference between `GET /api/devices` and `GET /api/devices/{id}`?
Answer: The collection endpoint returns multiple resources, while the item endpoint returns a single resource identified by its unique ID.

5. Why is statelessness important in REST?
Answer: Statelessness makes requests independent, simplifies horizontal scaling, and avoids server-side session coupling.

6. What does a `201 Created` response mean?
Answer: It means the server successfully created a new resource.

7. Why should APIs validate request payloads?
Answer: Validation protects data quality, prevents invalid state, and gives clients fast feedback when a contract is violated.

8. What is the purpose of an `ApiResponse<T>` wrapper?
Answer: It standardizes the response contract so clients can reliably parse status, messages, markers, payloads, and timestamps.

9. Why are DTOs used instead of returning JPA entities directly?
Answer: DTOs decouple external contracts from persistence models and reduce leakage of internal implementation details.

10. What is an IPv4 validation pattern used for in a device API?
Answer: It ensures that device IP addresses follow a valid dotted-decimal format before the request is accepted.

11. Why do enterprise APIs expose OpenAPI or Swagger docs?
Answer: They improve discoverability, client onboarding, testing, and contract clarity.

12. What is the difference between authentication and authorization?
Answer: Authentication verifies who the caller is, while authorization checks what that caller is allowed to do.

13. Why would a NOC platform protect delete operations more tightly than read operations?
Answer: Deletes are high-impact actions that can remove critical inventory or operational evidence, so they require stronger privilege control.

14. What is the role of Spring Actuator in a backend service?
Answer: It exposes operational endpoints such as health and info to help monitor service availability and runtime state.

15. Why should API errors be returned in a structured format?
Answer: Structured errors are easier for client applications, automation, and operators to parse consistently.

## Intermediate

16. Why is a search endpoint useful in a device management platform?
Answer: Operators often need to search by hostname, IP, vendor, model, or location instead of just by numeric ID.

17. When would you use query parameters instead of path variables?
Answer: Query parameters are better for optional filters, paging, sorting, and search criteria, while path variables are best for identity or fixed route segments.

18. Why is pagination important in enterprise REST APIs?
Answer: It protects the service and clients from excessive payload size and improves performance for large datasets.

19. What problem does a service layer solve in a Spring Boot application?
Answer: It centralizes business rules, coordinates repositories, and keeps controllers thin and focused on HTTP concerns.

20. Why are indexes important for fields like hostname, IP address, and timestamp?
Answer: They improve lookup and filtering performance for high-frequency operational queries.

21. What is the benefit of storing enums as strings in the database?
Answer: String values are easier to read, troubleshoot, and evolve safely than ordinal numbers.

22. Why should create and update flows check for uniqueness on hostname and IP address?
Answer: Duplicate identity data would break inventory accuracy and operational correlations.

23. How does MockMvc help with REST API testing?
Answer: It tests request handling, validation, security, serialization, and controller behavior without needing an external client.

24. Why might a dashboard endpoint aggregate counts instead of returning raw entities?
Answer: Dashboards are summary views intended for quick operational decisions rather than detailed record inspection.

25. What is the purpose of a global exception handler?
Answer: It converts different exception types into predictable HTTP responses and JSON error payloads.

26. Why is it useful to audit endpoint, method, status code, and execution time?
Answer: Those fields help with security investigations, compliance, latency analysis, and operational troubleshooting.

27. What is the difference between a domain entity and an audit log entity?
Answer: A domain entity represents business data, while an audit entity records system behavior and access history.

28. Why would a device metrics endpoint often return the newest metrics first?
Answer: Operators typically care most about current state and recent degradation signals.

29. What tradeoff exists when exposing Swagger and H2 console in a lab project?
Answer: They improve learning and debugging, but they would need stricter controls or removal in production.

30. Why should audit persistence failures not break the main business response?
Answer: Audit is important, but a monitoring backend should avoid turning secondary logging failures into unnecessary service outages.

31. What is the difference between `400 Bad Request` and `404 Not Found` in this project?
Answer: `400` means the request itself is invalid, while `404` means the requested resource does not exist.

32. Why might a backend team prefer HTTP Basic in a training module but OAuth2 in production?
Answer: HTTP Basic is smaller and easier to test, while OAuth2 provides stronger identity, token, and delegation patterns.

33. What is the purpose of deterministic seed data in a teaching or validation project?
Answer: It makes tests, examples, and smoke scripts repeatable across every run.

34. Why is an audit endpoint typically restricted to administrators?
Answer: Audit logs can expose sensitive operational behavior and should only be visible to trusted roles.

35. What can go wrong if tests share mutable database state?
Answer: Test order starts affecting outcomes, producing flaky results and hiding real regressions.

## Advanced

36. How would you evolve this module for multi-tenant OSS/BSS environments?
Answer: Add tenant-aware data boundaries, tenant-scoped authorization, per-tenant audit trails, and query isolation.

37. What are the design risks of turning every action into a separate REST endpoint?
Answer: The API can become RPC-like, inconsistent, and difficult to maintain compared with resource-oriented modeling.

38. How would you version this API without breaking existing NOC integrations?
Answer: Keep old versions stable, introduce a new version in parallel, document migration, and deprecate gradually with monitoring.

39. What is the operational value of tracking API execution time in audit logs?
Answer: It reveals slow endpoints, degraded dependencies, and client-visible latency trends.

40. Why might metrics be modeled as a separate resource instead of embedding them fully inside the device resource?
Answer: Metrics are time-series operational data with different access patterns, growth rate, and retention concerns.

41. How would you move from H2 to a production database safely?
Answer: Introduce migration tooling, externalized connection config, environment profiles, indexing reviews, and production-like integration tests.

42. What would you change if metrics volume grew from 50 rows to millions per day?
Answer: Use time-series partitioning, retention policies, async ingestion, bulk writes, and query optimization for hot versus cold data.

43. Why is idempotency important for some update operations?
Answer: It allows safe retries from clients and automation systems when network failures or timeouts occur.

44. How would you secure this API for external partner integrations?
Answer: Replace in-memory auth with identity federation, stronger secrets handling, TLS enforcement, scoped credentials, and rate limiting.

45. What problems can occur if controllers talk directly to repositories?
Answer: Business rules become duplicated, testing becomes harder, and HTTP concerns get mixed with persistence logic.

46. Why are audit logs important in regulated enterprise environments?
Answer: They provide traceability, accountability, forensic evidence, and compliance support.

47. How would you model partial updates if the API needed them later?
Answer: Introduce `PATCH` with carefully validated partial payloads and explicit field update semantics.

48. What is the difference between monitoring telemetry and audit logging?
Answer: Telemetry describes system or device behavior, while audit logs describe user or API actions against the platform.

49. How would you expose this platform to frontend dashboards without overloading the backend?
Answer: Use summary endpoints, caching where appropriate, pagination, filtered queries, and possibly event-driven updates for high-frequency views.

50. What makes this project more enterprise-grade than a basic CRUD demo?
Answer: It combines resource design, validation, role-based security, observability, audit persistence, deterministic data, test automation, documentation, and NOC-focused operational behavior.
