#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day10"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-10 MIB labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
  label="$1"
  class_name="$2"
  output_file="$CAPTURE_DIR/$3"
  echo "Running $label..."
  java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" SystemGroupExplorer exercise-01.log
run_and_capture "Exercise 02" OidHierarchyExplorer exercise-02.log
run_and_capture "Exercise 03" MibBrowserDemo exercise-03.log
run_and_capture "Exercise 04" EnterpriseMibDemo exercise-04.log
run_and_capture "Exercise 05" InterfaceMonitoringDemo exercise-05.log
run_and_capture "Exercise 06" TelecomMibMonitoringDemo exercise-06.log
run_and_capture "Exercise 07" SatComMibMonitoringDemo exercise-07.log
run_and_capture "Exercise 08" NocMibDashboard exercise-08.log

echo
echo "Validating output markers..."
grep -q "sysDescr" "$CAPTURE_DIR/exercise-01.log"
grep -q "mib-2" "$CAPTURE_DIR/exercise-02.log"
grep -q "sysName" "$CAPTURE_DIR/exercise-03.log"
grep -q "deviceTemperature" "$CAPTURE_DIR/exercise-04.log"
grep -q "ifOperStatus" "$CAPTURE_DIR/exercise-05.log"
grep -q "BER" "$CAPTURE_DIR/exercise-06.log"
grep -q "Eb/No" "$CAPTURE_DIR/exercise-07.log"
grep -q "NOC DASHBOARD" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-10 MIB exercises ran successfully."
