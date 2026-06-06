#!/usr/bin/env sh
set -eu
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
echo "Compiling Java sources..."
find . -name "*.java" > /tmp/day05_java_files.txt
javac @/tmp/day05_java_files.txt
echo "Compiled. Running smoke test: Exercise 01 (slave + master)"
cd Exercise-01-RS485-Bus-Basics
# run slave in background
java SlaveSimulator > /tmp/slave_out.log 2>&1 &
SPID=$!
sleep 0.5
java MasterSimulator > /tmp/master_out.log 2>&1
kill $SPID || true
echo "Master output:"
cat /tmp/master_out.log
echo "Slave log tail:"
tail -n 20 /tmp/slave_out.log || true
