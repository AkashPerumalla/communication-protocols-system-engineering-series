# Interview Questions - TCP vs UDP

## 1. What is TCP?
Answer: TCP is a connection-oriented transport protocol that provides reliable, ordered, and error-checked delivery of data.
Explanation: It is used when message integrity matters more than speed.

## 2. What is UDP?
Answer: UDP is a connectionless transport protocol that sends datagrams without establishing a session.
Explanation: It is useful for low-latency communication where occasional packet loss is acceptable.

## 3. What is the TCP three-way handshake?
Answer: It is the SYN, SYN-ACK, and ACK exchange used to establish a TCP connection.
Explanation: The handshake confirms that both sides are ready to communicate.

## 4. Why is TCP considered reliable?
Answer: TCP uses acknowledgements, sequencing, and retransmission to ensure delivery.
Explanation: Lost or out-of-order segments can be detected and corrected.

## 5. Why is UDP faster than TCP in many cases?
Answer: UDP avoids connection setup, acknowledgements, and retransmission overhead.
Explanation: Less protocol work often means lower latency.

## 6. What is a socket?
Answer: A socket is an endpoint used by applications to send and receive data over a network.
Explanation: It combines an IP address with a port for communication.

## 7. What is a port?
Answer: A port identifies a specific service or application on a device.
Explanation: For example, the Day 01 TCP lab uses port 9000.

## 8. What is packet loss?
Answer: Packet loss occurs when data packets fail to reach their destination.
Explanation: TCP retransmits lost data, while UDP typically does not.

## 9. What does ACK mean?
Answer: ACK stands for acknowledgement.
Explanation: It tells the sender that data was received successfully.

## 10. What is flow control in TCP?
Answer: Flow control prevents a fast sender from overwhelming a slow receiver.
Explanation: TCP uses windowing to manage sending rate.

## 11. What is congestion control?
Answer: Congestion control adjusts transmission rate to reduce network overload.
Explanation: It helps TCP respond to network conditions.

## 12. Does UDP guarantee order?
Answer: No, UDP does not guarantee packet order.
Explanation: Each datagram is independent.

## 13. Does UDP guarantee delivery?
Answer: No, UDP provides no delivery guarantee.
Explanation: The application must handle loss if it matters.

## 14. What is a datagram?
Answer: A datagram is a self-contained packet of data sent over UDP.
Explanation: It includes all information needed for routing and delivery.

## 15. Why do chat applications often use TCP?
Answer: They need reliable, ordered message delivery.
Explanation: Missing or reordered messages would hurt the user experience.

## 16. Why do streaming or gaming apps often use UDP?
Answer: They prioritize responsiveness over perfect delivery.
Explanation: A late packet is often less useful than a dropped one.

## 17. What is a server-side thread in socket programming?
Answer: It is a separate execution path that handles one client without blocking others.
Explanation: Threading allows concurrency in server applications.

## 18. What is RTT?
Answer: RTT means round-trip time.
Explanation: It measures how long a request takes to go to the server and come back.

## 19. How do you capture TCP handshake packets in Wireshark?
Answer: Start a capture, filter by the TCP port, and initiate a new connection.
Explanation: You can then inspect SYN, SYN-ACK, and ACK frames.

## 20. When should you choose TCP over UDP?
Answer: Choose TCP when correctness, ordering, and retransmission are required.
Explanation: Examples include file transfer, web traffic, and login sessions.

## 21. When should you choose UDP over TCP?
Answer: Choose UDP when low latency and simple packet delivery are more important than reliability.
Explanation: Examples include DNS, VoIP, and live media.

## 22. What is the main lesson of TCP vs UDP?
Answer: TCP optimizes for reliability, while UDP optimizes for lightweight speed.
Explanation: The right protocol depends on the application goal.
