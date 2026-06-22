#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8087}"
LOG_FILE="${LOG_FILE:-/tmp/day21-rest-api.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]]; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

curl_auth() {
  local userpass=$1
  shift
  curl -sf -u "${userpass}" "$@"
}

curl_auth_allow_error() {
  local userpass=$1
  shift
  curl -s -u "${userpass}" "$@"
}

echo "[1/6] Running Maven tests"
mvn -f "${APP_DIR}/pom.xml" -q clean test

echo "[2/6] Starting Day-21 app"
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

echo "[4/6] Validating required markers"
curl -sf "${BASE_URL}/api/platform" | grep -q "API PLATFORM ACTIVE"
curl_auth "viewer:viewer123" "${BASE_URL}/api/devices/1" | grep -q "DEVICE RETRIEVED"
curl_auth "viewer:viewer123" "${BASE_URL}/api/devices/search?q=Router" | grep -q "DEVICE SEARCH COMPLETE"
curl_auth "viewer:viewer123" "${BASE_URL}/api/metrics" | grep -q "METRICS RETRIEVED"
curl_auth "viewer:viewer123" "${BASE_URL}/api/dashboard" | grep -q "DASHBOARD GENERATED"
curl_auth "admin:admin123" "${BASE_URL}/api/audit" | grep -q "AUDIT LOG GENERATED"

echo "[5/6] Validating create, update, delete, and validation flows"
curl_auth "operator:operator123" -X POST "${BASE_URL}/api/devices" \
  -H "Content-Type: application/json" \
  -d '{"hostname":"Gateway-02","ipAddress":"10.21.5.82","deviceType":"IOT_GATEWAY","vendor":"Teltonika","model":"RUTX50","firmwareVersion":"7.07.1","location":"Remote-Site-13","status":"ONLINE"}' | tee /tmp/day21-create.json | grep -q "DEVICE CREATED"
grep -q "VALIDATION PASSED" /tmp/day21-create.json

curl_auth "operator:operator123" -X PUT "${BASE_URL}/api/devices/11" \
  -H "Content-Type: application/json" \
  -d '{"hostname":"Gateway-02","ipAddress":"10.21.5.82","deviceType":"IOT_GATEWAY","vendor":"Teltonika","model":"RUTX50","firmwareVersion":"7.07.2","location":"Remote-Site-13","status":"MAINTENANCE"}' | grep -q "DEVICE UPDATED"

curl_auth "admin:admin123" -X DELETE "${BASE_URL}/api/devices/11" | grep -q "DEVICE DELETED"

curl_auth_allow_error "operator:operator123" -X POST "${BASE_URL}/api/devices" \
  -H "Content-Type: application/json" \
  -d '{"hostname":"","ipAddress":"999.1.1.1","deviceType":"ROUTER","vendor":"Cisco","model":"Bad","firmwareVersion":"1.0","location":"","status":"ONLINE"}' | grep -q "REQUEST INVALID"

echo "[6/6] Validating security and status endpoints"
curl -sf "${BASE_URL}/actuator/health" | grep -q "UP"
curl -sf "${BASE_URL}/swagger-ui.html" >/dev/null

rm -f /tmp/day21-create.json

echo "PASS: Day-21 REST API validation completed successfully."
