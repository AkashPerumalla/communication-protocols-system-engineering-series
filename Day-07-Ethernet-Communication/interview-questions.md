# Interview Questions — Ethernet & Networking (50+)

Format: Question / Answer / Short Explanation

## Ethernet Fundamentals

1. Q: What is Ethernet? / A: A family of networking technologies for LANs. / Explains physical/data link layers.
2. Q: What layers of OSI does Ethernet implement? / A: Physical and Data Link. / Ethernet frames at Layer 2.
3. Q: Name three Ethernet speeds. / A: 10Mbps, 1Gbps, 10Gbps. / Historical progression.
4. Q: What is an Ethernet frame? / A: A packet structure with preamble, MACs, EtherType, payload, FCS. / Frame anatomy.
5. Q: What is EtherType 0x0800? / A: IPv4. / Protocol identification.
6. Q: What is FCS? / A: Frame Check Sequence (CRC). / Error detection.

## MAC Addressing

7. Q: How long is a MAC address? / A: 48 bits (6 bytes). / Typical representation.
8. Q: What is a broadcast MAC? / A: ff:ff:ff:ff:ff:ff. / Reaches all hosts on LAN.
9. Q: What indicates a multicast MAC? / A: Least significant bit of first octet = 1. / Multicast addressing rule.
10. Q: What is an OUI? / A: Organizationally Unique Identifier (first 3 bytes). / Manufacturer assignment.
11. Q: Difference between unicast and multicast? / A: Unicast to single MAC; multicast to group MAC. / Delivery semantics.

## ARP

12. Q: What does ARP do? / A: Resolves IP -> MAC on local network. / Neighbor discovery.
13. Q: ARP Request vs Reply? / A: Request is broadcast; reply is unicast containing MAC. / Basic flow.
14. Q: What is an ARP cache? / A: Table mapping IP to MAC with TTL. / Caching reduces broadcasts.
15. Q: What risks are associated with ARP? / A: ARP spoofing/poisoning. / Security concern.

## Switching

16. Q: What is MAC learning? / A: Switch records source MAC + ingress port. / Enables forwarding.
17. Q: What happens on unknown unicast? / A: Frame is flooded to all ports except ingress. / Learning switch behavior.
18. Q: What is a collision domain? / A: Network area where collisions can occur (hub-based). / Reduced by switches.
19. Q: What is a broadcast domain? / A: Area where broadcasts propagate. / VLANs segment broadcast domains.
20. Q: How do switches forward frames? / A: Based on MAC table lookups. / Layer-2 switching.

## VLANs

21. Q: What is a VLAN? / A: Virtual LAN - logical segmentation at Layer 2. / Network isolation.
22. Q: What is a trunk port? / A: Port carrying multiple VLANs with tagging (802.1Q). / Switch interconnect.
23. Q: How does a tagged frame look? / A: 802.1Q tag inserted after source MAC (TPID 0x8100). / Tag format.
24. Q: Can hosts in different VLANs communicate directly? / A: No; needs L3 routing. / Inter-VLAN routing.

## Network Monitoring

25. Q: Key metrics for monitoring? / A: Bandwidth, packet counts, errors, latency. / Health indicators.
26. Q: What is SNMP? / A: Simple Network Management Protocol for device management. / MIB-based.
27. Q: What is an SNMP trap? / A: Asynchronous notification from agent to manager. / Event signaling.

## Industrial Ethernet

28. Q: Name an industrial Ethernet protocol. / A: EtherNet/IP, Profinet, Modbus TCP. / Real-time industrial protocols.
29. Q: Why determinism matters? / A: Control systems need predictable latency and jitter. / Real-time requirements.

## Packet Analysis & Wireshark

30. Q: Wireshark filter for ARP? / A: `arp`. / Capture/display filter.
31. Q: How to filter ICMP echo? / A: `icmp` or `icmp.type == 8`. / Narrowing traffic.
32. Q: How to follow a TCP stream? / A: Right-click -> Follow -> TCP Stream. / Reconstruct payload.
33. Q: How to find DNS queries? / A: `dns` or `udp.port == 53`. / Protocol-specific filters.

## Packet Analysis Concepts

34. Q: What fields identify a TCP connection? / A: Source IP, source port, dest IP, dest port. / 4-tuple.
35. Q: What indicates TCP retransmission? / A: Duplicate ACKs and `tcp.analysis.retransmission`. / Troubleshooting.
36. Q: How to measure RTT? / A: Match TCP SYN to SYN-ACK timestamps or ICMP RTT. / Latency measurement.

## Troubleshooting

37. Q: Switch shows flapping MAC — cause? / A: Host moving ports or loop. / Investigate topology/loops.
38. Q: High collisions observed — why? / A: Half-duplex/hub or duplex mismatch. / Fix port config.
39. Q: Broadcast storm — what to check? / A: Loops, STP state, misconfigured devices. / Root cause.

## SatCom Networking

40. Q: What is a Hub Controller in SatCom? / A: Central element coordinating RTs and HPA. / Ground network component.
41. Q: Typical SatCom telemetry items? / A: Temperature, power, alarms, link status. / Health metrics.
42. Q: Why consider latency and jitter? / A: SatCom links add propagation delay affecting protocols. / Link characteristics.

## Advanced Ethernet

43. Q: What is Spanning Tree? / A: Protocol to prevent loops by blocking ports. / STP basics.
44. Q: How does link aggregation work? / A: Bundles multiple links for bandwidth and redundancy (LACP). / Port-channel.
45. Q: What is jumbo frame? / A: Ethernet frames > 1500 bytes (e.g., 9000). / MTU tuning.

## Security & Best Practices

46. Q: How to mitigate ARP spoofing? / A: Dynamic ARP inspection, static ARP, port security. / Defensive options.
47. Q: Best practice for VLAN design? / A: Principle of least privilege; separate management/servers/users. / Segmentation.

## Practical Exercises & Checks

48. Q: How to verify MAC learning? / A: Send frames and inspect switch MAC table. / Lab exercise.
49. Q: How to test ARP resolution? / A: Use `arp -a`, ping a host, and observe ARP requests/replies. / Hands-on test.
50. Q: How to capture a TCP handshake? / A: Start capture, initiate connection, and filter by IP/port. / Capture exercise.

## Extra

51. Q: What is VLAN tagging vs VLAN access port? / A: Tagged carries VLAN ID; access port assigns untagged traffic to VLAN. / Behavior difference.
52. Q: How to monitor device health without SNMP? / A: Use syslog, streaming telemetry, or API polling. / Alternatives.
53. Q: What metrics matter for SatCom uplink? / A: Power amplifier (HPA) health, BER, link margin. / RF-focused.
54. Q: How to validate CRC on frames? / A: Compare CRC field to computed CRC32 (note Ethernet bit-order specifics). / Validation caveat.
55. Q: When to use static MAC entries? / A: Security or for hosts that must not move (e.g., servers). / Use with care.
