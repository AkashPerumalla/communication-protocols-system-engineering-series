#!/usr/bin/env sh
set -eu

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
OUT_DIR="$ROOT_DIR/out/day09"
CAPTURE_DIR="$ROOT_DIR/captures"

mkdir -p "$OUT_DIR" "$CAPTURE_DIR"

echo "Compiling Day-09 SNMP Manager vs Agent labs..."
find "$ROOT_DIR" -name '*.java' -print0 | xargs -0 javac --release 17 -d "$OUT_DIR"

run_and_capture() {
  label="$1"
  class_name="$2"
  output_file="$CAPTURE_DIR/$3"
  echo "Running $label..."
  java -cp "$OUT_DIR" "$class_name" | tee "$output_file"
}

run_and_capture "Exercise 01" SNMPManager manager-agent.log
run_and_capture "Exercise 02" MultiOidPollingDemo multi-oid-polling.log
run_and_capture "Exercise 03" MibBrowserDemo mib-browser.log
run_and_capture "Exercise 04" MultiDeviceMonitoringDemo multi-device.log
run_and_capture "Exercise 05" TelecomAgentMonitoringDemo telecom-monitoring.log
run_and_capture "Exercise 06" TrapEnabledAgentDemo trap-processing.log
run_and_capture "Exercise 07" AgentFailureDetectionDemo agent-failure.log
run_and_capture "Exercise 08" NocDashboardSimulator noc-dashboard.log

echo
echo "Validating output markers..."
grep -q "Router-01" "$CAPTURE_DIR/manager-agent.log"
grep -q "CPU:" "$CAPTURE_DIR/multi-oid-polling.log"
grep -q "OID:" "$CAPTURE_DIR/mib-browser.log"
grep -q "Device Status Dashboard" "$CAPTURE_DIR/multi-device.log" || grep -q "ONLINE" "$CAPTURE_DIR/multi-device.log"
grep -q "BER:" "$CAPTURE_DIR/telecom-monitoring.log"
grep -q "TRAP RECEIVED" "$CAPTURE_DIR/trap-processing.log"
grep -q "DEVICE_UNREACHABLE" "$CAPTURE_DIR/agent-failure.log"
grep -q "NETWORK OPERATIONS CENTER" "$CAPTURE_DIR/noc-dashboard.log"

echo "PASS: All Day-09 SNMP exercises ran successfully."
