#!/usr/bin/env bash
set -euo pipefail
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
OUT="$ROOT_DIR/out"
mkdir -p "$OUT"
echo "Compiling all Day-07 Java sources..."
javac --release 17 -d "$OUT" $(find "$ROOT_DIR" -name '*.java')
echo "Compile finished. To run demos, execute scripts/run-exNN.sh per exercise or run them manually:"
echo "  bash scripts/run-ex01.sh"
