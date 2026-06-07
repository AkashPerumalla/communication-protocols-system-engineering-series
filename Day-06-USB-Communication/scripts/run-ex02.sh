#!/usr/bin/env sh
set -euo pipefail
ROOT=$(cd "$(dirname "$0")/.." && pwd)
javac --release 17 -d "$ROOT/out" $(find "$ROOT" -name 'Descriptor*.java')
echo "Run analyzer: java -cp $ROOT/out Exercise02.DescriptorAnalyzer"
