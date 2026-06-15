#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day14"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-14 SNMP Versions labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
    label="$1"
    class_name="$2"
    output_file="$3"
    echo "Running $label..."
    java -cp "$OUT_DIR" "$class_name" | tee "$CAPTURE_DIR/$output_file"
}

run_and_capture "Exercise 01" Snmpv1BasicsDemo exercise-01.log
run_and_capture "Exercise 02" Snmpv2cCommunicationDemo exercise-02.log
run_and_capture "Exercise 03" Snmpv3AuthenticationDemo exercise-03.log
run_and_capture "Exercise 04" Snmpv3EncryptionDemo exercise-04.log
run_and_capture "Exercise 05" VersionComparisonDemo exercise-05.log
run_and_capture "Exercise 06" TelecomMonitoringDemo exercise-06.log
run_and_capture "Exercise 07" SecureNocMonitoringDemo exercise-07.log
run_and_capture "Exercise 08" VersionMigrationSimulatorDemo exercise-08.log

echo
echo "Validating output markers..."
grep -q "SNMPv1 SUCCESS" "$CAPTURE_DIR/exercise-01.log"
grep -q "SNMPv2c SUCCESS" "$CAPTURE_DIR/exercise-02.log"
grep -q "AUTH SUCCESS" "$CAPTURE_DIR/exercise-03.log"
grep -q "ENCRYPTION ACTIVE" "$CAPTURE_DIR/exercise-04.log"
grep -q "VERSION COMPARISON" "$CAPTURE_DIR/exercise-05.log"
grep -q "TELECOM MONITORING" "$CAPTURE_DIR/exercise-06.log"
grep -q "SECURE NOC" "$CAPTURE_DIR/exercise-07.log"
grep -q "MIGRATION COMPLETE" "$CAPTURE_DIR/exercise-08.log"

echo "PASS: All Day-14 SNMP Version exercises completed successfully."
