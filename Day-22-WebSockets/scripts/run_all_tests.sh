#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8088}"
LOG_FILE="${LOG_FILE:-/tmp/day22-websocket-monitoring.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]]; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

echo "[1/6] Running Maven tests (REST + WebSocket integrations)"
mvn -f "${APP_DIR}/pom.xml" -q clean test

echo "[2/6] Starting Day-22 application"
mvn -f "${APP_DIR}/pom.xml" -q -DskipTests spring-boot:run > "${LOG_FILE}" 2>&1 &
APP_PID=$!

echo "[3/6] Waiting for API readiness"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/platform" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

if ! curl -sf "${BASE_URL}/api/platform" >/dev/null 2>&1; then
  echo "Application failed to start"
  cat "${LOG_FILE}"
  exit 1
fi

echo "[4/6] Validating REST APIs and required markers"
curl -sf "${BASE_URL}/api/platform" | grep -q "REALTIME MONITORING ACTIVE"
curl -sf "${BASE_URL}/api/devices" | grep -q "REALTIME MONITORING ACTIVE"
curl -sf "${BASE_URL}/api/telemetry" | grep -q "TELEMETRY STREAM ACTIVE"
curl -sf "${BASE_URL}/api/alarms" | grep -q "ALARM BROADCASTED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "DASHBOARD UPDATED"
curl -sf "${BASE_URL}/api/clients" | grep -q "CLIENT CONNECTED"
curl -sf -X POST "${BASE_URL}/api/alarms/1/acknowledge" | grep -q "ALARM ACKNOWLEDGED"

echo "[5/6] Validating WebSocket endpoint contract"
STATUS_PAYLOAD="$(curl -sf "${BASE_URL}/api/platform")"
echo "${STATUS_PAYLOAD}" | grep -q "/ws-monitoring"
echo "${STATUS_PAYLOAD}" | grep -q "/topic/telemetry"
echo "${STATUS_PAYLOAD}" | grep -q "/topic/alarms"
echo "${STATUS_PAYLOAD}" | grep -q "/topic/dashboard"
echo "${STATUS_PAYLOAD}" | grep -q "/topic/clients"

echo "[6/6] Validating WebSocket topic broadcasts via integration reports"
grep -q "Tests run: 1, Failures: 0, Errors: 0" "${APP_DIR}/target/surefire-reports/com.sky2dev.day22.websocket.WebSocketConfigurationIntegrationTest.txt"
grep -q "Tests run: 1, Failures: 0, Errors: 0" "${APP_DIR}/target/surefire-reports/com.sky2dev.day22.websocket.WebSocketBroadcastIntegrationTest.txt"

echo "PASS: Day-22 WebSocket Real-Time Monitoring validation completed successfully."
