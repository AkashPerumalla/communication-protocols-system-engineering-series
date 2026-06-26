#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8080}"
IMAGE_NAME="${IMAGE_NAME:-day25-device-monitoring:latest}"

cleanup() {
  cd "${APP_DIR}"
  docker compose down -v >/dev/null 2>&1 || true
}
trap cleanup EXIT

cd "${APP_DIR}"

echo "[1/7] Running Maven clean test"
mvn -q clean test

echo "[2/7] Building Docker image"
docker build -t "${IMAGE_NAME}" . >/dev/null

echo "[3/7] Starting Docker Compose stack"
docker compose up -d --build >/dev/null

echo "[4/7] Waiting for MySQL and Spring health"
for _ in {1..90}; do
  MYSQL_HEALTH="$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}unknown{{end}}' day25-mysql 2>/dev/null || echo unknown)"
  APP_HEALTH="$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}unknown{{end}}' day25-spring-app 2>/dev/null || echo unknown)"
  if [[ "${MYSQL_HEALTH}" == "healthy" && "${APP_HEALTH}" == "healthy" ]]; then
    break
  fi
  sleep 2
done

if [[ "$(docker inspect --format='{{.State.Health.Status}}' day25-spring-app 2>/dev/null || echo unhealthy)" != "healthy" ]]; then
  echo "Spring app did not become healthy"
  docker compose logs --no-color spring-app mysql | tail -n 120
  exit 1
fi

echo "[5/7] Executing REST API smoke tests"
PLATFORM_RESPONSE="$(curl -sf "${BASE_URL}/api/platform/status")"
DEVICE_RESPONSE="$(curl -sf "${BASE_URL}/api/devices")"
TELEMETRY_RESPONSE="$(curl -sf "${BASE_URL}/api/telemetry")"
ALARM_RESPONSE="$(curl -sf "${BASE_URL}/api/alarms")"
DASHBOARD_RESPONSE="$(curl -sf "${BASE_URL}/api/dashboard")"
REPORT_RESPONSE="$(curl -sf "${BASE_URL}/api/reports")"
NOTIFICATION_RESPONSE="$(curl -sf "${BASE_URL}/api/notifications")"

echo "[6/7] Validating markers"
grep -q "DOCKER READY" <<< "${PLATFORM_RESPONSE}"
grep -q "APPLICATION RUNNING" <<< "${PLATFORM_RESPONSE}"
grep -q "APPLICATION RUNNING" <<< "${DEVICE_RESPONSE}"
grep -q "TELEMETRY COLLECTED" <<< "${TELEMETRY_RESPONSE}"
grep -q "ALARM GENERATED" <<< "${ALARM_RESPONSE}"
grep -q "DASHBOARD UPDATED" <<< "${DASHBOARD_RESPONSE}"
grep -q "NOTIFICATION SENT" <<< "${DASHBOARD_RESPONSE}"
grep -q "REPORT GENERATED" <<< "${REPORT_RESPONSE}"
grep -q "NOTIFICATION SENT" <<< "${NOTIFICATION_RESPONSE}"

echo "[7/7] PASS: Day-25 Docker platform validation completed successfully"
