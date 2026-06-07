#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
echo "Compiling Day-06 Java sources..."
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name '*.java')
echo "Compilation complete. Running diagnostics..."
java -cp "$ROOT/out" Exercise08.USBDiagnostics
echo "Done. If USBDiagnostics reported OK, basic smoke tests passed."
