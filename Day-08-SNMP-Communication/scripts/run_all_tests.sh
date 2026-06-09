#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-08 SNMP Communication labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
  local label="$1"
  local class_name="$2"
  local output_file="$CAPTURE_DIR/$3"
  echo "Running $label..."
  java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" SNMPManager snmp-get.log
run_and_capture "Exercise 02" SNMPWalkDemo snmp-walk.log
run_and_capture "Exercise 03" SNMPSetDemo snmp-set.log
run_and_capture "Exercise 04" SNMPTrapDemo snmp-trap.log
run_and_capture "Exercise 05" NetworkDeviceMonitoringDemo network-monitoring.log
run_and_capture "Exercise 06" TelecomDeviceMonitoringDemo telecom-monitoring.log
run_and_capture "Exercise 07" SNMPAlarmManagerDemo alarm-manager.log
run_and_capture "Exercise 08" NMSDashboardSimulator nms-dashboard.log

echo
echo "Validating output markers..."
grep -q "Router-01" "$CAPTURE_DIR/snmp-get.log"
grep -q "1.3.6.1.2.1.1.1.0" "$CAPTURE_DIR/snmp-walk.log"
grep -q "REJECTED" "$CAPTURE_DIR/snmp-set.log"
grep -q "TRAP RECEIVED" "$CAPTURE_DIR/snmp-trap.log"
grep -q "CPU Usage" "$CAPTURE_DIR/network-monitoring.log"
grep -q "BER =" "$CAPTURE_DIR/telecom-monitoring.log"
grep -q "ACTIVE ALARMS" "$CAPTURE_DIR/alarm-manager.log"
grep -q "NETWORK MANAGEMENT DASHBOARD" "$CAPTURE_DIR/nms-dashboard.log"

echo "PASS: All Day-08 SNMP exercises ran successfully."
