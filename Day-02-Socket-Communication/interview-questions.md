# Interview Questions - Socket Communication (25)

1. Question: What is a socket?
Answer: A socket is an endpoint for sending or receiving data across a network.
Explanation: It pairs an IP address with a port number and provides APIs for I/O.

2. Question: What is the difference between `ServerSocket` and `Socket` in Java?
Answer: `ServerSocket` listens for incoming connections; `Socket` represents a client or accepted connection.
Explanation: `ServerSocket.accept()` returns a `Socket` for communication.

3. Question: What is blocking I/O with sockets?
Answer: Blocking I/O causes the thread to wait until data is available or an operation completes.
Explanation: Calls like `readLine()` block the executing thread.

4. Question: How do you prevent blocking from stalling a server thread?
Answer: Use timeouts (`setSoTimeout`) or non-blocking/asynchronous I/O.
Explanation: Timeouts throw exceptions; async APIs use callbacks or selectors.

5. Question: What is `SocketTimeoutException`?
Answer: An exception thrown when a read operation exceeds the socket's configured timeout.
Explanation: It indicates no data arrived within the time window.

6. Question: Why use `try-with-resources` for sockets?
Answer: Ensures sockets and streams are closed automatically when done.
Explanation: Prevents resource leaks and simplifies cleanup logic.

7. Question: How do you support multiple clients in a TCP server?
Answer: Use one thread per client (thread pool) or async I/O (NIO) to handle concurrent connections.
Explanation: Each approach balances simplicity vs scalability.

8. Question: What problems can arise from spawning an unbounded number of threads?
Answer: Resource exhaustion and increased context-switching overhead.
Explanation: Use bounded thread pools and backpressure strategies.

9. Question: How would you implement graceful shutdown for a server?
Answer: Stop accepting new connections, signal worker threads to finish, and close resources.
Explanation: Use shared flags and `ExecutorService.shutdown()`.

10. Question: What is TCP three-way handshake?
Answer: SYN, SYN-ACK, ACK sequence to establish a TCP connection.
Explanation: It synchronizes sequence numbers and confirms both sides are ready.

11. Question: What TCP flags indicate data transfer and termination?
Answer: PSH/ACK for data push; FIN/ACK for connection termination.
Explanation: FIN starts close; ACK acknowledges receipt.

12. Question: What is the difference between graceful close and abortive close?
Answer: Graceful close sends FIN and waits for acknowledgement; abortive close terminates immediately.
Explanation: Graceful close allows buffered data to flush.

13. Question: How can you log client identifiers safely in a multi-threaded server?
Answer: Generate thread-safe unique IDs (AtomicInteger) and use synchronized or structured logging.
Explanation: Avoid shared mutable logging state; include IDs in log messages.

14. Question: What metrics are useful for socket servers?
Answer: Active connections, average connection duration, messages/sec, error rates.
Explanation: Helps detect load and reliability issues.

15. Question: How do you measure connection duration?
Answer: Record a timestamp at connect and at disconnect; compute the difference.
Explanation: Use `Instant` or `System.nanoTime()` for precision.

16. Question: What is `ExecutorService` useful for?
Answer: Managing a pool of worker threads for task execution.
Explanation: Simplifies concurrency and lifecycle management of threads.

17. Question: How to handle exceptions from worker threads so they don't get lost?
Answer: Use logging, Futures, or an UncaughtExceptionHandler to capture errors.
Explanation: Without handlers, exceptions may terminate threads silently.

18. Question: When might you prefer NIO over thread-per-client model?
Answer: For very high concurrency (thousands of connections) where threads are expensive.
Explanation: NIO uses selectors and fewer threads to multiplex connections.

19. Question: How do you add a read timeout to a socket in Java?
Answer: Call `socket.setSoTimeout(milliseconds)` before blocking read.
Explanation: Read operations throw `SocketTimeoutException` when the timeout is reached.

20. Question: What are common causes of `SocketException`?
Answer: Network failures, closed sockets, improper resource handling.
Explanation: Check networking stack and resource lifecycle.

21. Question: How should you handle client input validation?
Answer: Validate protocol framing, lengths, allowed characters and timeouts.
Explanation: Protects against malformed data and simple DoS attacks.

22. Question: How does TCP ensure ordered, reliable delivery?
Answer: Sequence numbers, ACKs, retransmission and flow control.
Explanation: Ensures data arrives intact and in order.

23. Question: What is the effect of `SO_KEEPALIVE`?
Answer: Sends periodic probes to detect broken connections.
Explanation: Helps detect dead peers when no data is exchanged.

24. Question: How to test socket behavior deterministically?
Answer: Use local loopback with scripted clients, mock sockets, and integration tests.
Explanation: Local tests reduce network variability and make assertions reliable.

25. Question: How to capture and analyze socket traffic with Wireshark?
Answer: Run a capture, use filters like `tcp.port == 9002`, inspect SYN/ACK/FIN and payload.
Explanation: Wireshark shows headers, flags, and payload to reason about protocol behavior.
