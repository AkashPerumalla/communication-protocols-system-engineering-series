#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'USBStorage*.java' -o -name 'DeviceInfoCollector*.java')
echo "Run storage inspector: java -cp $ROOT/out Exercise05.USBStorageScanner"
