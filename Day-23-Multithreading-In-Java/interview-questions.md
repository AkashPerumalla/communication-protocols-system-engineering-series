# Day-23 Multithreading Interview Questions

## Beginner

1. Question: What is multithreading in Java?
Answer: Multithreading is concurrent execution of multiple threads within a single Java process.
Explanation: It allows monitoring systems to handle telemetry, alarms, and UI updates simultaneously.

2. Question: Why is multithreading important for NOC applications?
Answer: NOC applications process many independent streams in parallel.
Explanation: Telemetry ingestion and alarm handling must not block each other.

3. Question: What is the difference between a thread and a process?
Answer: A process has isolated memory, while threads share memory inside a process.
Explanation: Shared memory improves speed but introduces synchronization risks.

4. Question: How do you create a thread in Java directly?
Answer: Extend Thread or instantiate Thread with a Runnable target.
Explanation: Direct thread creation is educational but less scalable than executors.

5. Question: What is Runnable used for?
Answer: Runnable defines a unit of work with no return value.
Explanation: It is common for long-running tasks like collectors and consumers.

6. Question: What is Callable used for?
Answer: Callable defines work that returns a value and can throw checked exceptions.
Explanation: Useful for expensive computations like link-budget calculation.

7. Question: What is ExecutorService?
Answer: ExecutorService manages thread pools and task execution.
Explanation: It avoids creating too many unmanaged threads.

8. Question: Why use a fixed thread pool?
Answer: It limits concurrency and protects system resources.
Explanation: In monitoring platforms, pool sizing prevents overload under burst traffic.

9. Question: What is a ScheduledExecutorService?
Answer: It executes tasks at fixed delay or fixed rate.
Explanation: Good for recurring health checks and report jobs.

10. Question: What does Future represent?
Answer: Future represents a pending result of asynchronous execution.
Explanation: It enables waiting with timeout and cancellation.

11. Question: What is CompletableFuture?
Answer: CompletableFuture is an async abstraction that supports chaining and composition.
Explanation: It simplifies fan-out/fan-in workflows for dashboard aggregation.

12. Question: What is thread safety?
Answer: Thread safety means correct behavior under concurrent access.
Explanation: Shared counters and caches need synchronization or atomic structures.

13. Question: What is synchronization in Java?
Answer: Synchronization serializes access to shared critical sections.
Explanation: It prevents lost updates and inconsistent states.

14. Question: What is AtomicInteger?
Answer: AtomicInteger performs lock-free atomic numeric updates.
Explanation: It is efficient for counters used by concurrent metrics.

15. Question: What is a race condition?
Answer: A race condition occurs when output depends on timing of concurrent operations.
Explanation: Unsafe increments often produce lower-than-expected counts.

16. Question: What is a BlockingQueue?
Answer: BlockingQueue coordinates producers and consumers with built-in blocking operations.
Explanation: It is a core primitive for reliable telemetry pipelines.

17. Question: What is the producer-consumer pattern?
Answer: Producers create data and consumers process data through a shared queue.
Explanation: It decouples ingestion rate from processing rate.

18. Question: What is graceful shutdown?
Answer: Graceful shutdown stops accepting new work and terminates active work safely.
Explanation: It avoids data loss during maintenance or deployment.

## Intermediate

19. Question: Why assign custom thread names?
Answer: Custom names improve observability and troubleshooting.
Explanation: Logs quickly reveal whether telemetry, alarm, or notification pools are saturated.

20. Question: What is the difference between fixedDelay and fixedRate scheduling?
Answer: fixedDelay waits after completion; fixedRate targets constant interval from start times.
Explanation: fixedDelay is safer for variable-duration maintenance jobs.

21. Question: How does Future timeout help reliability?
Answer: Timeout prevents indefinite waits on blocked tasks.
Explanation: NOC APIs can return quickly with controlled failure behavior.

22. Question: How does CompletableFuture.allOf() work?
Answer: It completes when all supplied futures complete.
Explanation: It is ideal for combining independent service calls.

23. Question: What happens if one future in allOf fails?
Answer: The combined future completes exceptionally.
Explanation: Error handling must recover or provide degraded responses.

24. Question: Why separate executors by workload type?
Answer: Isolation prevents one workload from starving another.
Explanation: Alarm processing should continue even if report generation slows down.

25. Question: What is backpressure in producer-consumer systems?
Answer: Backpressure controls producer speed when consumer throughput is lower.
Explanation: Bounded queues and timeouts reduce memory pressure.

26. Question: What are common shared-state bugs in monitoring systems?
Answer: Lost counters, stale states, and out-of-order updates.
Explanation: Correctness needs synchronization design and deterministic tests.

27. Question: Why are deterministic markers useful in automated tests?
Answer: They provide stable string-based assertions in API responses.
Explanation: CI scripts can verify workflows with curl and grep.

28. Question: Why should async tasks write execution logs?
Answer: Execution logs provide audit trail and performance visibility.
Explanation: Task name, thread, duration, and status support root-cause analysis.

29. Question: What is thread starvation?
Answer: Starvation occurs when tasks cannot get CPU or pool slots in time.
Explanation: Mis-sized pools or blocking calls can starve critical tasks.

30. Question: How do you cancel executor tasks safely?
Answer: Use Future.cancel(true) and cooperative interruption handling.
Explanation: Tasks should check interruption and release resources quickly.

31. Question: Why avoid heavy blocking calls on shared pools?
Answer: Blocking calls reduce throughput for unrelated tasks.
Explanation: Dedicated pools or async I/O can isolate blocking behavior.

32. Question: What is the role of @PreDestroy in threaded services?
Answer: It provides a lifecycle hook to stop workers and clear resources.
Explanation: It ensures clean shutdown on application exit.

33. Question: How can you model thread lifecycle states for demos?
Answer: Use controlled waits, notifications, sleeps, and joins.
Explanation: This makes NEW, RUNNABLE, WAITING, TIMED_WAITING, and TERMINATED observable.

34. Question: Why should scheduler logic be toggleable in tests?
Answer: Background jobs can introduce nondeterministic test behavior.
Explanation: Disabling scheduling keeps tests stable and reproducible.

35. Question: What is an anti-pattern when learning multithreading?
Answer: Print-only demos disconnected from business workflows.
Explanation: Real systems require persistence, queues, and lifecycle management.

36. Question: How do AtomicInteger and synchronized compare?
Answer: AtomicInteger is lock-free for simple operations; synchronized is broader and simpler for complex critical sections.
Explanation: Choice depends on contention level and operation complexity.

37. Question: Why monitor queue size in production?
Answer: Queue depth indicates processing lag and saturation.
Explanation: It can trigger scaling or protective throttling policies.

38. Question: How do you avoid duplicate alarm notifications?
Answer: Use dedup rules and idempotent notification logic.
Explanation: Duplicate alerts cause operator fatigue and missed incidents.

## Advanced

39. Question: How do you design thread pools for mixed latency workloads?
Answer: Partition pools by workload criticality and expected execution time.
Explanation: Fast-path alarms should not share queue with long report tasks.

40. Question: How does executor rejection policy impact reliability?
Answer: Rejection policy decides behavior when queues are full.
Explanation: Caller-runs, drop, or fail-fast each has different operational risk.

41. Question: What is head-of-line blocking in monitoring pipelines?
Answer: Slow tasks at the front delay later tasks in the same queue.
Explanation: Separate queues/pools reduce this effect.

42. Question: How do you test race conditions deterministically?
Answer: Increase contention with high iteration counts and controlled synchronization points.
Explanation: Unsafe counters often reveal non-deterministic loss under stress.

43. Question: Why is graceful shutdown part of SRE reliability?
Answer: It preserves in-flight operations and system integrity during restarts.
Explanation: Abrupt termination can drop telemetry and alerts.

44. Question: How do you combine consistency and throughput in shared metrics?
Answer: Use atomic structures for hot counters and synchronized blocks for compound updates.
Explanation: Hybrid design balances correctness and performance.

45. Question: When should CompletableFuture use custom executors?
Answer: When default common pool could be overloaded or unsuitable.
Explanation: Custom executors provide isolation and predictable behavior.

46. Question: What are key observability dimensions for multithreaded services?
Answer: Queue depth, pool utilization, task latency, failure rate, and rejection count.
Explanation: These metrics expose hidden concurrency bottlenecks.

47. Question: How do you prevent cascading failures across async components?
Answer: Add timeouts, retries with limits, bulkheads, and fallback responses.
Explanation: Isolation and bounded retries reduce blast radius.

48. Question: How should alarm severity influence concurrency strategy?
Answer: Critical alarms should use fast lanes with higher priority resources.
Explanation: Severity-aware routing improves incident response time.

49. Question: What makes a multithreaded design enterprise-ready?
Answer: Deterministic behavior, thread safety, observability, lifecycle controls, and automated validation.
Explanation: Production readiness is architecture plus operational discipline.

50. Question: How does this Day 23 module map to real SatCom operations?
Answer: It models concurrent telemetry ingestion, threshold alarms, notification fan-out, dashboard aggregation, and maintenance controls.
Explanation: The same concurrency patterns appear in real mission and network monitoring centers.
