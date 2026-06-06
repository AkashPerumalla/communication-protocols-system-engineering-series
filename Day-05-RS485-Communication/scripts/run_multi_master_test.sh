#!/usr/bin/env sh
set -eu
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
echo "Compiling Java sources..."
find . -name "*.java" > /tmp/day05_java_files.txt
javac @/tmp/day05_java_files.txt

cd Exercise-02-MultiDevice-Network
echo "Starting Device01..3"
java Device01 > /tmp/dev1.log 2>&1 &
PID1=$!
java Device02 > /tmp/dev2.log 2>&1 &
PID2=$!
java Device03 > /tmp/dev3.log 2>&1 &
PID3=$!
sleep 0.5

cd ../Exercise-02-MultiMaster-Arbitration
echo "Starting BusArbiter"
java BusArbiter > /tmp/arbiter.log 2>&1 &
ARB=$!
sleep 0.5
echo "Starting TokenMasterA and TokenMasterB"
java TokenMasterA > /tmp/tma.log 2>&1 &
TMA=$!
java TokenMasterB > /tmp/tmb.log 2>&1 &
TMB=$!

sleep 4
echo "--- Arbiter log ---"
tail -n 50 /tmp/arbiter.log || true
echo "--- Master A log ---"
tail -n 50 /tmp/tma.log || true
echo "--- Master B log ---"
tail -n 50 /tmp/tmb.log || true

kill $TMA $TMB $ARB $PID1 $PID2 $PID3 || true
