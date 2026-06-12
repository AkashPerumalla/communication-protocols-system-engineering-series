#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day11"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-11 OID labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
    label="$1"
    class_name="$2"
    output_file="$CAPTURE_DIR/$3"
    echo "Running $label..."
    java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" OidBasicsDemo exercise-01.log
run_and_capture "Exercise 02" OidHierarchyExplorer exercise-02.log
run_and_capture "Exercise 03" OidLookupTool exercise-03.log
run_and_capture "Exercise 04" SnmpGetSimulatorDemo exercise-04.log
run_and_capture "Exercise 05" SnmpWalkSimulatorDemo exercise-05.log
run_and_capture "Exercise 06" TelecomOidMonitoringDemo exercise-06.log
run_and_capture "Exercise 07" SatComOidMonitoringDemo exercise-07.log
run_and_capture "Exercise 08" NocOidDashboardDemo exercise-08.log

echo
echo "Validating output markers..."
grep -q "sysName" "$CAPTURE_DIR/exercise-01.log"
grep -q "OID:" "$CAPTURE_DIR/exercise-01.log"
grep -q "Hierarchy" "$CAPTURE_DIR/exercise-02.log"
grep -q "GET Response" "$CAPTURE_DIR/exercise-04.log"
grep -q "WALK Result" "$CAPTURE_DIR/exercise-05.log"
grep -q "BER" "$CAPTURE_DIR/exercise-06.log"
grep -q "Eb/No" "$CAPTURE_DIR/exercise-07.log"
grep -q "NOC DASHBOARD" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-11 OID exercises completed successfully."
