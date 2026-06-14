#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day13"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-13 SNMP Traps and Notifications labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
    label="$1"
    class_name="$2"
    output_file="$CAPTURE_DIR/$3"
    echo "Running $label..."
    java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" TrapBasicsDemo exercise-01.log
run_and_capture "Exercise 02" TrapReceiverDemo exercise-02.log
run_and_capture "Exercise 03" TrapProcessingDemo exercise-03.log
run_and_capture "Exercise 04" AlarmManagementDemo exercise-04.log
run_and_capture "Exercise 05" TelecomTrapsDemo exercise-05.log
run_and_capture "Exercise 06" SatComTrapsDemo exercise-06.log
run_and_capture "Exercise 07" EventCorrelationDemo exercise-07.log
run_and_capture "Exercise 08" NocTrapDashboardDemo exercise-08.log

echo
echo "Validating output markers..."
grep -q "TRAP GENERATED" "$CAPTURE_DIR/exercise-01.log"
grep -q "TRAP RECEIVED" "$CAPTURE_DIR/exercise-02.log"
grep -q "TRAP PROCESSED" "$CAPTURE_DIR/exercise-03.log"
grep -q "ALARM CREATED" "$CAPTURE_DIR/exercise-04.log"
grep -q "BER ALARM" "$CAPTURE_DIR/exercise-05.log"
grep -q "SATCOM ALARM" "$CAPTURE_DIR/exercise-06.log"
grep -q "ROOT CAUSE" "$CAPTURE_DIR/exercise-07.log"
grep -q "NOC DASHBOARD" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-13 SNMP Trap exercises completed successfully."#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day13"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-13 SNMP Traps and Notifications labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
    label="$1"
    class_name="$2"
    output_file="$CAPTURE_DIR/$3"
    echo "Running $label..."
    java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" TrapBasicsDemo exercise-01.log
run_and_capture "Exercise 02" TrapReceiverDemo exercise-02.log
run_and_capture "Exercise 03" TrapProcessingDemo exercise-03.log
run_and_capture "Exercise 04" AlarmManagementDemo exercise-04.log
run_and_capture "Exercise 05" TelecomTrapsDemo exercise-05.log
run_and_capture "Exercise 06" SatComTrapsDemo exercise-06.log
run_and_capture "Exercise 07" EventCorrelationDemo exercise-07.log
run_and_capture "Exercise 08" NocTrapDashboardDemo exercise-08.log

echo
echo "Validating output markers..."
grep -q "TRAP GENERATED" "$CAPTURE_DIR/exercise-01.log"
grep -q "TRAP RECEIVED" "$CAPTURE_DIR/exercise-02.log"
grep -q "TRAP PROCESSED" "$CAPTURE_DIR/exercise-03.log"
grep -q "ALARM CREATED" "$CAPTURE_DIR/exercise-04.log"
grep -q "BER ALARM" "$CAPTURE_DIR/exercise-05.log"
grep -q "SATCOM ALARM" "$CAPTURE_DIR/exercise-06.log"
grep -q "ROOT CAUSE" "$CAPTURE_DIR/exercise-07.log"
grep -q "NOC DASHBOARD" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-13 SNMP Trap exercises completed successfully."
