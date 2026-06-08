#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-02..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-02-MAC-Learning-Switch"/*.java
echo "Running EthernetSwitch demo"
java -cp "$ROOT_DIR/out" EthernetSwitch
