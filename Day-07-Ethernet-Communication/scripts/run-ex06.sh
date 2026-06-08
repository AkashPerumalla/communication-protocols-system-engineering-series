#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-06..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-06-Network-Monitoring-System"/*.java
echo "Running NetworkMonitor"
java -cp "$ROOT_DIR/out" NetworkMonitor
