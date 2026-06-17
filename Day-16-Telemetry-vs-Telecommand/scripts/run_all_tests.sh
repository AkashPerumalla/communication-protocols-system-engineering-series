#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8082}"
LOG_FILE="${LOG_FILE:-/tmp/day16-telemetry-vs-telecommand.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

(cd "${APP_DIR}" && mvn -f pom.xml test -q)

(cd "${APP_DIR}" && mvn -f pom.xml -q -DskipTests org.springframework.boot:spring-boot-maven-plugin:3.3.2:run >"${LOG_FILE}" 2>&1) &
APP_PID=$!

for _ in {1..60}; do
  if curl -sf "${BASE_URL}/api/dashboard" >/dev/null 2>&1; then
    break
  fi
  sleep 2
done

curl -sf "${BASE_URL}/api/telemetry" | grep -q "TELEMETRY RECEIVED"
curl -sf -X POST "${BASE_URL}/api/commands/restart/1" | grep -q "COMMAND EXECUTED"
curl -sf -X POST "${BASE_URL}/api/commands/enable-interface/1" | grep -q "STATE UPDATED"
curl -sf -X POST "${BASE_URL}/api/commands/change-frequency/1" | grep -q "TELECOM CONTROL"
curl -sf -X POST "${BASE_URL}/api/commands/reset-modem/4" | grep -q "SATCOM COMMAND"
curl -sf -X POST "${BASE_URL}/api/commands/switch-backup/4" | grep -q "REMOTE RECOVERY"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "AUTO CORRECTION"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "NOC CONTROL DASHBOARD"

echo "PASS: All Day-16 Telemetry vs Telecommand exercises completed successfully."
