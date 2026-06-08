#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-01..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-01-Ethernet-Frame-Analyzer"/*.java
echo "Running EthernetFrameAnalyzer"
java -cp "$ROOT_DIR/out" EthernetFrameAnalyzer
