# Packet Analysis - Annotated Examples

## ARP
- See `arp-request.txt` for a request example. Key fields: `eth.dst`, `eth.src`, `arp.opcode`, `arp.sender`, `arp.target`.

## ICMP (Ping)
- `icmp.type` 8 = echo request, 0 = echo reply. `ip.ttl` and round-trip time are useful for latency checks.

## TCP
- Follow the 3-way handshake using `tcp.flags` and `tcp.seq`/`tcp.ack`. For retransmissions check `tcp.analysis.retransmission`.

## DNS
- Match queries with responses via transaction ID and source/destination IPs; check `dns.qry.name` and `dns.a` records.

## HTTP
- Inspect `http.request.method`, `http.response.code`, and headers; use `Follow TCP Stream` for payloads.
