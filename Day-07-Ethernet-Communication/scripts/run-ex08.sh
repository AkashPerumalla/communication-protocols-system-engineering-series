#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "Compiling Exercise-08..."
javac --release 17 -d "$ROOT_DIR/out" "$ROOT_DIR/Exercise-08-SatCom-Ground-Network"/*.java
echo "Running HubController"
java -cp "$ROOT_DIR/out" HubController
