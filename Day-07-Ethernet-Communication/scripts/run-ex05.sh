#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-05..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-05-VLAN-Network-Simulator"/*.java
echo "Running VLANSwitch demo"
java -cp "$ROOT_DIR/out" VLANSwitch
