#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8083}"
LOG_FILE="${LOG_FILE:-/tmp/day17-alarm-monitoring.log}"
APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

(cd "${APP_DIR}" && mvn -q test)

(cd "${APP_DIR}" && mvn -q -DskipTests spring-boot:run >"${LOG_FILE}" 2>&1) &
APP_PID=$!

for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

curl -sf "${BASE_URL}/api/metrics" | grep -q "THRESHOLD DETECTED"
curl -sf "${BASE_URL}/api/alarms" | grep -q "ALARM GENERATED"
curl -sf "${BASE_URL}/api/alarms/critical" | grep -q "SEVERITY ASSIGNED"
curl -sf -X POST "${BASE_URL}/api/alarms/1/acknowledge" | grep -q "ALARM ACKNOWLEDGED"
curl -sf -X POST "${BASE_URL}/api/alarms/1/escalate" | grep -q "ALARM ESCALATED"
curl -sf -X POST "${BASE_URL}/api/alarms/1/resolve" | grep -q "ALARM RESOLVED"
curl -sf -X POST "${BASE_URL}/api/alarms/1/close" | grep -q "ALARM CLOSED"
curl -sf "${BASE_URL}/api/rca" | grep -q "ROOT CAUSE IDENTIFIED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "ALARM CORRELATED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "NOC ALARM DASHBOARD"

echo "PASS: All Day-17 Alarm Monitoring exercises completed successfully."
