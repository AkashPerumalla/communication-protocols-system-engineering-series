#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'AndroidUsbHostExample*.java')
echo "Android host example (host-side) is a read-only demo. Run: java -cp $ROOT/out Exercise07.AndroidUsbHostExample"
