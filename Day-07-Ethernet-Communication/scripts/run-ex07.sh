#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-07..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-07-Industrial-Ethernet"/*.java
echo "Running MonitoringStation"
java -cp "$ROOT_DIR/out" MonitoringStation
