#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-04..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-04-Wireshark-Packet-Lab"/*.java
echo "Running PacketCaptureAnalyzer"
java -cp "$ROOT_DIR/out" PacketCaptureAnalyzer
