#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8086}"
LOG_FILE="${LOG_FILE:-/tmp/day20-monitoring.log}"
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

echo "[2/6] Starting Day-20 app"
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
curl -sf "${BASE_URL}/api/agents" | grep -q "AGENT REGISTERED"
curl -sf "${BASE_URL}/api/metrics" | grep -q "METRICS COLLECTED"
curl -sf "${BASE_URL}/api/alerts" | grep -q "ALERT GENERATED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "DASHBOARD UPDATED"
curl -sf "${BASE_URL}/api/snmp" | grep -q "SNMP MONITORING ACTIVE"
curl -sf "${BASE_URL}/api/stream/status" | grep -q "REALTIME STREAM ACTIVE"

curl -sf -X POST "${BASE_URL}/api/notifications/send" \
  -H "Content-Type: application/json" \
  -d '{"channel":"EMAIL","recipient":"noc@example.com","message":"Critical threshold breach"}' | grep -q "NOTIFICATION SENT"

curl -sf -X POST "${BASE_URL}/api/reports/generate" \
  -H "Content-Type: application/json" \
  -d '{"reportType":"Daily Monitoring Report"}' | grep -q "REPORT GENERATED"

echo "[5/6] Validating required API workflows"
curl -sf -X POST "${BASE_URL}/api/agents/register" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"agent-20","hostname":"edge-probe-20","ipAddress":"10.0.20.20","location":"Regional-NOC"}' | grep -q "AGENT REGISTERED"

curl -sf -X POST "${BASE_URL}/api/agents/heartbeat" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"agent-20"}' | grep -q "AGENT REGISTERED"

curl -sf -X POST "${BASE_URL}/api/alerts/evaluate" \
  -H "Content-Type: application/json" \
  -d '{"agentId":"agent-03"}' | grep -q "ALERT GENERATED"

echo "[6/6] Verifying WebSocket endpoint configuration"
curl -sf "${BASE_URL}/api/stream/status" | grep -q "/ws-monitoring"
curl -sf "${BASE_URL}/api/stream/status" | grep -q "/topic/metrics"
curl -sf "${BASE_URL}/api/stream/status" | grep -q "/topic/alerts"
curl -sf "${BASE_URL}/api/stream/status" | grep -q "/topic/dashboard"

echo "PASS: Day-20 Real-Time Monitoring validation completed successfully."
