#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8090}"
LOG_FILE="${LOG_FILE:-/tmp/day24-scheduling.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]]; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

echo "[1/6] Running Maven clean test"
mvn -f "${APP_DIR}/pom.xml" -q clean test

echo "[2/6] Starting Spring Boot application"
mvn -f "${APP_DIR}/pom.xml" -q -DskipTests spring-boot:run > "${LOG_FILE}" 2>&1 &
APP_PID=$!

echo "[3/6] Waiting for readiness"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/platform/status" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

curl -sf "${BASE_URL}/api/platform/status" >/dev/null

echo "[4/6] Calling APIs"
PLATFORM_RESPONSE="$(curl -sf "${BASE_URL}/api/platform/status")"
TELEMETRY_RESPONSE="$(curl -sf "${BASE_URL}/api/telemetry")"
ALARM_RESPONSE="$(curl -sf "${BASE_URL}/api/alarms")"
REPORT_RESPONSE="$(curl -sf "${BASE_URL}/api/reports")"
DASHBOARD_RESPONSE="$(curl -sf "${BASE_URL}/api/dashboard")"
TASK_RESPONSE="$(curl -sf "${BASE_URL}/api/tasks/executions")"

RUN_TELEMETRY="$(curl -sf -X POST "${BASE_URL}/api/tasks/run/telemetry")"
RUN_ALARM="$(curl -sf -X POST "${BASE_URL}/api/tasks/run/alarm")"
RUN_REPORT="$(curl -sf -X POST "${BASE_URL}/api/tasks/run/report")"
RUN_ARCHIVE="$(curl -sf -X POST "${BASE_URL}/api/tasks/run/archive")"
RUN_RECOVERY="$(curl -sf -X POST "${BASE_URL}/api/tasks/run/recovery")"

echo "[5/6] Validating markers"
grep -q "SCHEDULER ACTIVE" <<< "${PLATFORM_RESPONSE}"
grep -q "TELEMETRY COLLECTED" <<< "${TELEMETRY_RESPONSE}"
grep -q "ALARM GENERATED" <<< "${ALARM_RESPONSE}"
grep -q "REPORT GENERATED" <<< "${REPORT_RESPONSE}"
grep -q "DASHBOARD UPDATED" <<< "${DASHBOARD_RESPONSE}"
grep -q "SCHEDULER ACTIVE" <<< "${TASK_RESPONSE}"

grep -q "TELEMETRY COLLECTED" <<< "${RUN_TELEMETRY}"
grep -q "ALARM GENERATED" <<< "${RUN_ALARM}"
grep -q "REPORT GENERATED" <<< "${RUN_REPORT}"
grep -q "DATA ARCHIVED" <<< "${RUN_ARCHIVE}"
grep -q "AUTO RECOVERY COMPLETE" <<< "${RUN_RECOVERY}"

echo "[6/6] PASS: Day-24 Scheduling automation checks completed successfully"
