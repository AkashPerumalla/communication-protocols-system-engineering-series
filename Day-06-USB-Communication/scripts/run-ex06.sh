#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'USBPacket*.java' -o -name 'USBPacketAnalyzer*.java')
echo "Run packet simulator: java -cp $ROOT/out Exercise06.USBPacketSimulator"
echo "Then run analyzer: java -cp $ROOT/out Exercise06.USBPacketAnalyzer"
