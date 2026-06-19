#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="http://localhost:8084"
APP_PID=""

cleanup() {
  if [[ -n "$APP_PID" ]]; then
    kill "$APP_PID" >/dev/null 2>&1 || true
    wait "$APP_PID" 2>/dev/null || true
  fi
}
trap cleanup EXIT

cd "$ROOT_DIR"

echo "[1/4] Running mvn clean test"
mvn -q clean test

echo "[2/4] Starting Spring Boot app"
mvn -q -DskipTests spring-boot:run > /tmp/day18_app.log 2>&1 &
APP_PID=$!

echo "[3/4] Waiting for API readiness"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

if ! curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
  echo "Application failed to start"
  cat /tmp/day18_app.log
  exit 1
fi

echo "[4/4] Validating endpoints and markers"

curl -sf "${BASE_URL}/api/devices" | grep -q "HUB MONITORING ACTIVE"
curl -sf "${BASE_URL}/api/alarms" | grep -q "ALARM GENERATED"
curl -sf -X POST "${BASE_URL}/api/control/reboot/5" \
  -H "Content-Type: application/json" \
  -d '{"commandPayload":"auto-reboot","executedBy":"run_all_tests"}' | grep -q "CONTROL EXECUTED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "DASHBOARD UPDATED"
curl -sf "${BASE_URL}/api/notifications" | grep -q "NOTIFICATION SENT"
curl -sf "${BASE_URL}/api/reports" | grep -q "REPORT GENERATED"
curl -sf -X POST "${BASE_URL}/api/automation/recover/5" \
  -H "Content-Type: application/json" \
  -d '{"triggeredBy":"run_all_tests"}' | grep -q "AUTO RECOVERY COMPLETE"
curl -sf "${BASE_URL}/api/noc" | grep -q "NOC CONTROL CENTER"

# Required API surface validation
curl -sf "${BASE_URL}/api/devices/1" >/dev/null
curl -sf "${BASE_URL}/api/metrics" >/dev/null
curl -sf "${BASE_URL}/api/notifications" >/dev/null
curl -sf "${BASE_URL}/api/reports" >/dev/null
curl -sf -X POST "${BASE_URL}/api/control/reset-interface/5" -H "Content-Type: application/json" -d '{"commandPayload":"reset","executedBy":"run_all_tests"}' >/dev/null
curl -sf -X POST "${BASE_URL}/api/control/enable-port/5" -H "Content-Type: application/json" -d '{"commandPayload":"enable","executedBy":"run_all_tests"}' >/dev/null
curl -sf -X POST "${BASE_URL}/api/control/disable-port/5" -H "Content-Type: application/json" -d '{"commandPayload":"disable","executedBy":"run_all_tests"}' >/dev/null
curl -sf -X POST "${BASE_URL}/api/control/change-config/5" -H "Content-Type: application/json" -d '{"commandPayload":"qos=high","executedBy":"run_all_tests"}' >/dev/null
curl -sf -X POST "${BASE_URL}/api/alarms/1/acknowledge" -H "Content-Type: application/json" -d '{"acknowledgedBy":"run_all_tests"}' >/dev/null

echo "All markers validated successfully"
