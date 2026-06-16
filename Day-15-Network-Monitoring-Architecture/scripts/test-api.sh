#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
LOG_FILE="${LOG_FILE:-/tmp/day15-network-monitoring.log}"
APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
MVN_CMD="mvn"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

if ! curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
  (cd "${APP_DIR}" && ${MVN_CMD} -q -DskipTests spring-boot:run >"${LOG_FILE}" 2>&1) &
  APP_PID=$!

  for _ in {1..60}; do
    if curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
      break
    fi
    sleep 2
  done
fi

curl -sf "${BASE_URL}/api/dashboard" >/dev/null
curl -sf "${BASE_URL}/api/devices" >/dev/null
curl -sf "${BASE_URL}/api/events" >/dev/null
curl -sf "${BASE_URL}/api/alerts" >/dev/null

echo "PASS: Day-15 Network Monitoring Architecture validated successfully."
