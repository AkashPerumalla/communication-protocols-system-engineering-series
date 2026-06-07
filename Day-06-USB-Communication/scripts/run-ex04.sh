#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'CDC*.java')
echo "Run CDC simulator: java -cp $ROOT/out Exercise04.CDCDeviceSimulator"
echo "Then run console: java -cp $ROOT/out Exercise04.CDCConsole"
