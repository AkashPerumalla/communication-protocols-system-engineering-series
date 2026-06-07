#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'HID*.java')
echo "Run HID simulator: java -cp $ROOT/out Exercise03.HIDDeviceSimulator"
echo "Then run reader: java -cp $ROOT/out Exercise03.HIDEventReader"
