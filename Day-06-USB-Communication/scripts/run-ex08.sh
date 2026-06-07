#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'USBDiagnostics*.java' -o -name 'USBHealthMonitor*.java')
echo "Run diagnostics: java -cp $ROOT/out Exercise08.USBDiagnostics"
