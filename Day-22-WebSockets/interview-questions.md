# Day 22 - WebSocket Interview Questions (Beginner to Advanced)

## Beginner
1. What problem does WebSocket solve compared to plain HTTP?
2. How does WebSocket communication differ from request-response APIs?
3. What is the purpose of the HTTP Upgrade header in WebSocket handshake?
4. What status code indicates a successful WebSocket handshake?
5. What are the basic WebSocket connection lifecycle states?
6. What is full-duplex communication and why is it important for real-time systems?
7. What are common use cases where WebSocket is preferred over polling?
8. What is STOMP and why is it often used with Spring WebSocket?
9. What is the role of a WebSocket endpoint in Spring Boot?
10. What is SockJS and when should it be enabled?
11. How do topics work in STOMP pub-sub systems?
12. What is the difference between `/app/*` and `/topic/*` destinations?
13. Why do monitoring dashboards often rely on WebSockets?
14. How do WebSockets improve alarm notification latency in NOC systems?
15. What is message fan-out in real-time event systems?

## Intermediate
16. How do you configure a STOMP broker in Spring Boot?
17. How do you track active client sessions in a WebSocket application?
18. What are common causes of WebSocket handshake failures (HTTP 400/403/500)?
19. How do you validate incoming STOMP payloads in Spring?
20. How can you acknowledge alarms through WebSocket commands?
21. How do you design topic names for multi-domain monitoring data?
22. How do you avoid tight coupling between REST APIs and WebSocket publishers?
23. What strategies help avoid duplicate alarms in threshold-based systems?
24. How do scheduled telemetry jobs interact with WebSocket broadcast pipelines?
25. How do you model alarm severity levels for operations platforms?
26. How would you handle disconnected clients and stale sessions?
27. Why are deterministic seed datasets valuable in integration testing?
28. How can you integration-test WebSocket broadcast behavior in Spring Boot?
29. What metrics should be collected for WebSocket observability?
30. How do you secure a WebSocket endpoint in enterprise deployments?
31. How do you handle backpressure when telemetry publish rate is high?
32. What are differences between WebSocket and Server-Sent Events (SSE)?
33. How can you test marker-based response contracts for operational APIs?
34. Why should alarm acknowledgement be idempotent?
35. How do you design API responses for both machine parsing and operator readability?

## Advanced
36. How do you horizontally scale WebSocket sessions across multiple application nodes?
37. When should you replace Spring simple broker with external broker relay?
38. How do sticky sessions impact WebSocket reliability in load-balanced clusters?
39. How do you design regional topic partitioning for global NOC deployments?
40. What failure modes occur during broker outages and how do you mitigate them?
41. How do you guarantee message ordering for telemetry and alarm events?
42. How do you implement replay/recovery for clients that reconnect after downtime?
43. How do you model multi-tenant isolation for WebSocket subscriptions?
44. What authentication and authorization model fits STOMP destination-level control?
45. How would you audit WebSocket commands for compliance-sensitive operations?
46. How do you detect and prevent noisy-alert storms in real-time systems?
47. What architecture would you propose for SatCom hub monitoring with WebSockets?
48. How would you optimize payload design for high-frequency low-latency dashboards?
49. How would you correlate REST-triggered state changes with WebSocket event streams?
50. How do you design a resilient real-time monitoring platform that survives partial failures while maintaining operator trust?
