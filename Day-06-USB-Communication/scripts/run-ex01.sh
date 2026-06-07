#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'USBEnumerator*.java')
echo "Run simulator in one terminal: java -cp $ROOT/out Exercise01.USBEnumerator"
echo "Then run client: java -cp $ROOT/out Exercise01.USBEnumeratorClient"
