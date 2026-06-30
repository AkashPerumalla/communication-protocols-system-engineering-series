# Day-29 - AI Agent Interview Questions (50)

1. Question: What differentiates an AI agent from a chatbot?
   Answer: An AI agent plans and executes actions using tools and memory, while a chatbot typically only generates conversational text.
   Explanation: Agents include decision logic and external system interaction loops.

2. Question: Why is deterministic mode useful in enterprise agents?
   Answer: It produces reproducible behavior for testing and incident replay.
   Explanation: Stable outputs reduce flaky CI and simplify debugging.

3. Question: What is a planning stage in agent architecture?
   Answer: A stage that converts user intent into executable tool actions.
   Explanation: Planning improves traceability over direct one-shot generation.

4. Question: Why should tool selection be explicit?
   Answer: Explicit selection supports auditing and controllability.
   Explanation: Operations teams need to know why a tool was called.

5. Question: What is grounded response generation?
   Answer: Producing answers based on observed tool outputs rather than free-form guesses.
   Explanation: Grounding reduces hallucination risk.

6. Question: Why use response envelopes in APIs?
   Answer: They standardize success, marker, message, payload, and timestamp.
   Explanation: Clients can parse all responses uniformly.

7. Question: What are marker constants?
   Answer: Deterministic status tokens emitted by workflows and APIs.
   Explanation: They enable strict machine-verifiable QA assertions.

8. Question: Why is memory important in agents?
   Answer: It preserves conversation and execution context across turns.
   Explanation: Without memory, the agent cannot maintain continuity.

9. Question: What is a tool registry?
   Answer: A central component that stores tool metadata and handlers.
   Explanation: It enables discovery, governance, and invocation control.

10. Question: Why validate SQL as read-only?
    Answer: To prevent destructive queries in operational platforms.
    Explanation: Query tooling must enforce least-risk behavior.

11. Question: What does constructor-based DI improve?
    Answer: Testability and clear dependency boundaries.
    Explanation: Components can be replaced in tests without hidden global state.

12. Question: Why add request IDs in middleware?
    Answer: To trace requests across logs and services.
    Explanation: Correlation IDs are fundamental for incident analysis.

13. Question: What does global exception handling provide?
    Answer: Uniform, contract-safe error responses.
    Explanation: It prevents inconsistent API failure payloads.

14. Question: Why should file tools include path guards?
    Answer: To prevent directory traversal and unauthorized access.
    Explanation: Tool security is critical in enterprise environments.

15. Question: What is MCP in this context?
    Answer: A protocol pattern for exposing tools through model-facing interfaces.
    Explanation: It standardizes tool invocation boundaries.

16. Question: Why include both `/agent/chat` and `/agent/run`?
    Answer: They separate conversational intent and explicit task execution semantics.
    Explanation: Different clients can choose the contract that fits their workflow.

17. Question: How should an agent expose history?
    Answer: Through session-scoped endpoints returning ordered events.
    Explanation: Operators need replayable context per incident.

18. Question: What does graceful degradation mean for optional PostgreSQL?
    Answer: The platform continues running while the DB tool reports degraded mode.
    Explanation: Core control plane remains available despite dependency outages.

19. Question: Why keep tools modular?
    Answer: Modular tools are easier to test, secure, and evolve independently.
    Explanation: Tight coupling slows change and increases regression risk.

20. Question: What makes a planner explainable?
    Answer: It returns rationale, confidence, selected tool, and execution steps.
    Explanation: Explainability builds trust with operations teams.

21. Question: Why use typed DTOs with Pydantic?
    Answer: They enforce schema correctness at API boundaries.
    Explanation: Invalid payloads are rejected early with clear errors.

22. Question: How is structured logging different from plain logging?
    Answer: Structured logging emits parseable JSON fields.
    Explanation: It improves indexing and analytics in observability tools.

23. Question: What is an orchestration trace?
    Answer: Marker sequence emitted during reason-plan-execute-memory lifecycle.
    Explanation: It proves which stages actually executed.

24. Question: Why should tool outputs be deterministic in tests?
    Answer: Determinism prevents random failures and unstable snapshots.
    Explanation: CI reliability depends on repeatable assertions.

25. Question: What is the role of health endpoints?
    Answer: They provide machine-readable service readiness.
    Explanation: Orchestrators and scripts use them for liveness checks.

26. Question: Why include platform status endpoints?
    Answer: To aggregate system, agent, and dependency status in one place.
    Explanation: It reduces troubleshooting latency for NOC engineers.

27. Question: What is a safe default tool in planners?
    Answer: A low-risk, diagnostic tool like system_info.
    Explanation: Unknown intents should fail safely.

28. Question: How can agent plans be tested?
    Answer: Use deterministic input queries and assert exact selected tools and markers.
    Explanation: Plan-level unit tests catch regressions early.

29. Question: Why is replayability valuable in telecom operations?
    Answer: It allows teams to reproduce and audit incident decisions.
    Explanation: Replayable sessions improve postmortem quality.

30. Question: What is the difference between memory store and database?
    Answer: Memory store is a runtime context layer; database is persistence infrastructure.
    Explanation: They solve different concerns and can be decoupled.

31. Question: Why should REST tool clients enforce timeouts?
    Answer: To avoid hanging threads and cascading failures.
    Explanation: Bounded latency is essential for production resilience.

32. Question: What does SOLID bring to agent backends?
    Answer: Better maintainability and predictable extension points.
    Explanation: New tools and planners can be added with minimal disruption.

33. Question: Why test both API and orchestration layers?
    Answer: API correctness does not guarantee internal workflow correctness.
    Explanation: End-to-end confidence requires both layers.

34. Question: How do markers help QA automation?
    Answer: Scripts can assert deterministic states without brittle text matching.
    Explanation: Marker checks are robust and simple.

35. Question: Why is knowledge_search useful in NOC agent systems?
    Answer: It correlates telemetry, alarms, and logs for faster diagnosis.
    Explanation: Cross-source context is key for triage.

36. Question: What risks come from unrestricted tool invocation?
    Answer: Data exfiltration, unauthorized actions, and unstable behavior.
    Explanation: Tool invocation must be policy-governed.

37. Question: Why is explicit session_id required?
    Answer: It scopes memory and trace retrieval to the correct conversation.
    Explanation: Multi-user systems need isolated context.

38. Question: What is an OpenAI-compatible abstraction in this project?
    Answer: A service interface that can swap real API calls with deterministic local mock behavior.
    Explanation: It preserves future provider portability.

39. Question: How should degraded dependencies be represented in responses?
    Answer: With explicit status fields and stable markers.
    Explanation: Ambiguous failures slow operator decisions.

40. Question: Why should planners return confidence?
    Answer: Confidence informs fallback or human-in-the-loop policies.
    Explanation: Low confidence can trigger safer paths.

41. Question: What test categories are expected in enterprise agent repos?
    Answer: Unit, integration, workflow, and health/degraded-mode tests.
    Explanation: Layered tests provide broad reliability coverage.

42. Question: Why include MCP tests if HTTP APIs already exist?
    Answer: MCP is another integration surface with its own failure modes.
    Explanation: Transport-specific validation is necessary.

43. Question: What is the benefit of optional PostgreSQL in training projects?
    Answer: Students can run the platform without infra prerequisites.
    Explanation: Optional DB keeps onboarding friction low.

44. Question: Why should scripts print a final PASS marker?
    Answer: CI and humans need an unambiguous completion signal.
    Explanation: It simplifies pipeline parsing and triage.

45. Question: How does global exception mapping improve client UX?
    Answer: Clients always receive predictable error shape and status.
    Explanation: Consumers avoid custom parsing per endpoint.

46. Question: Why separate models and DTO folders?
    Answer: Models define core contracts; DTOs define API boundary contracts.
    Explanation: Separation clarifies intent and ownership.

47. Question: What is a practical fallback strategy when tool selection is uncertain?
    Answer: Select a safe diagnostic tool and report reduced confidence.
    Explanation: Safe fallback avoids harmful or noisy execution.

48. Question: How can this design evolve to multi-agent architecture?
    Answer: Add specialized planners/orchestrators and a coordinator router.
    Explanation: Current modular boundaries support horizontal extension.

49. Question: Why is marker ordering important?
    Answer: Order proves workflow progression integrity.
    Explanation: Correct sequencing validates orchestration design.

50. Question: What defines production-quality learning material for AI agents?
    Answer: Runnable code, deterministic tests, observability, error handling, docs, and automation.
    Explanation: Enterprise readiness requires end-to-end completeness.
