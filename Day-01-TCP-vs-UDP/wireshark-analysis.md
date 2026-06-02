# Wireshark Analysis

This guide explains how to capture and interpret TCP and UDP traffic from the Day 01 labs using Wireshark.

## TCP Analysis

### What to Look For

- `SYN` from client to server.
- `SYN-ACK` from server to client.
- `ACK` from client to server.
- Application payload after the handshake completes.
- Retransmissions if packets are lost or delayed.

### Why It Matters

TCP establishes a session before application data flows. That session setup is visible in the packet trace and helps explain reliability and ordering.

## UDP Analysis

### What to Look For

- Individual datagrams going directly to the server.
- No handshake packets.
- Packet size and source port changes as clients restart.
- Optional response packets if the lab sends acknowledgements.

### Why It Matters

UDP traffic is easier to send but harder to coordinate. The trace shows why it is considered connectionless.

## Step-By-Step Capture Instructions

1. Install and open Wireshark.
2. Select your active network interface, such as Wi-Fi or Ethernet.
3. Start capturing packets before launching the lab client.
4. For TCP, run the server on port `9000` and then start the TCP client.
5. For UDP, run the server on port `9001` and then send UDP packets from the client.
6. Use display filters such as `tcp.port == 9000` or `udp.port == 9001`.
7. Click a packet and expand the protocol tree to inspect headers and payload.
8. Save the capture as a `.pcapng` file if you want to keep it in `wireshark-captures/`.

## Suggested Learning Prompts

- How many packets were needed to establish the TCP session?
- Were any TCP segments retransmitted?
- How does the UDP trace differ from TCP when the client sends three packets?
- What source port did the client use for each run?

## Recommended Filters

- `tcp.port == 9000`
- `udp.port == 9001`
- `ip.addr == 127.0.0.1`

## Portfolio Tip

Export a few packet screenshots and annotate them in your README or poster so recruiters can quickly see that you understand the protocol behavior, not just the code.
