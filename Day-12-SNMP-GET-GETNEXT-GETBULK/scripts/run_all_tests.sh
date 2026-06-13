#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day12"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-12 SNMP labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
    label="$1"
    class_name="$2"
    output_file="$CAPTURE_DIR/$3"
    echo "Running $label..."
    java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" SnmpGetSimulatorDemo exercise-01.log
run_and_capture "Exercise 02" SnmpGetNextSimulatorDemo exercise-02.log
run_and_capture "Exercise 03" SnmpGetBulkSimulatorDemo exercise-03.log
run_and_capture "Exercise 04" OidDiscoveryDemo exercise-04.log
run_and_capture "Exercise 05" PerformanceComparisonDemo exercise-05.log
run_and_capture "Exercise 06" NetworkMonitoringDemo exercise-06.log
run_and_capture "Exercise 07" TelecomMonitoringDemo exercise-07.log
run_and_capture "Exercise 08" NocDashboardDemo exercise-08.log

echo
echo "Validating output markers..."
grep -q "GET Response" "$CAPTURE_DIR/exercise-01.log"
grep -q "GETNEXT Result" "$CAPTURE_DIR/exercise-02.log"
grep -q "GETBULK Result" "$CAPTURE_DIR/exercise-03.log"
grep -q "OID Discovery" "$CAPTURE_DIR/exercise-04.log"
grep -q "Performance Comparison" "$CAPTURE_DIR/exercise-05.log"
grep -q "CPU" "$CAPTURE_DIR/exercise-06.log"
grep -q "BER" "$CAPTURE_DIR/exercise-07.log"
grep -q "NOC DASHBOARD" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-12 SNMP GET/GETNEXT/GETBULK exercises completed successfully."
