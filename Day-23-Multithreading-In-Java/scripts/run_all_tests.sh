#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8089}"
LOG_FILE="${LOG_FILE:-/tmp/day23-multithreading.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]]; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

echo "[1/6] Running Maven tests"
mvn -f "${APP_DIR}/pom.xml" -q clean test

echo "[2/6] Starting Day-23 application"
mvn -f "${APP_DIR}/pom.xml" -q -DskipTests spring-boot:run > "${LOG_FILE}" 2>&1 &
APP_PID=$!

echo "[3/6] Waiting for API readiness"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

if ! curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
  echo "Application failed to start"
  cat "${LOG_FILE}"
  exit 1
fi

echo "[4/6] Validating required deterministic markers"
curl -sf "${BASE_URL}/api/devices" | grep -q "THREAD POOL ACTIVE"
curl -sf "${BASE_URL}/api/telemetry" | grep -q "TELEMETRY COLLECTED"
curl -sf "${BASE_URL}/api/alarms" | grep -q "ALARM GENERATED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "MULTITHREADING ACTIVE"
curl -sf "${BASE_URL}/api/threads/status" | grep -q "THREAD CREATED"
curl -sf "${BASE_URL}/api/threads/statistics" | grep -q "THREAD POOL ACTIVE"
curl -sf "${BASE_URL}/api/threads/performance" | grep -q "THREAD SYNCHRONIZED"
curl -sf "${BASE_URL}/api/threads/callable-demo" | grep -q "CALLABLE EXECUTED"
curl -sf "${BASE_URL}/api/threads/completable-future-demo" | grep -q "COMPLETABLE FUTURE COMPLETED"

echo "[5/6] Validating simulation start and stop workflows"
curl -sf -X POST "${BASE_URL}/api/threads/start-simulation" | grep -q "TASK EXECUTED"
sleep 2
curl -sf -X POST "${BASE_URL}/api/threads/stop-simulation" | grep -q "NOTIFICATION SENT"

echo "[6/6] Validation complete"
echo "PASS: Day-23 Multithreading monitoring validation completed successfully."
