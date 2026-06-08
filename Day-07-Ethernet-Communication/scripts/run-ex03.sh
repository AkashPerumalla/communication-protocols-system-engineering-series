#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-03..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-03-ARP-Protocol-Simulator"/*.java
echo "Running ARPSimulator"
java -cp "$ROOT_DIR/out" ARPSimulator
