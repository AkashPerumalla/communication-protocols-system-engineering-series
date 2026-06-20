#!/usr/bin/env bash
set -euo pipefail

APP_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BASE_URL="${BASE_URL:-http://localhost:8085}"
LOG_FILE="${LOG_FILE:-/tmp/day19-satcom.log}"
APP_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]]; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
    wait "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

echo "[1/5] Running Maven tests"
mvn -f "${APP_DIR}/pom.xml" -q clean test

echo "[2/5] Starting Day-19 app"
mvn -f "${APP_DIR}/pom.xml" -q -DskipTests spring-boot:run > "${LOG_FILE}" 2>&1 &
APP_PID=$!

echo "[3/5] Waiting for API readiness"
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

echo "[4/5] Validating deterministic markers"

curl -sf "${BASE_URL}/api/satellites" | grep -q "SATELLITE NETWORK ACTIVE"
curl -sf "${BASE_URL}/api/link-budget" | grep -q "LINK BUDGET CALCULATED"
curl -sf "${BASE_URL}/api/frequency-plan" | grep -q "FREQUENCY PLAN GENERATED"
curl -sf "${BASE_URL}/api/metrics" | grep -q "LINK MONITORING ACTIVE"
curl -sf "${BASE_URL}/api/alarms" | grep -q "SATCOM ALARM GENERATED"
curl -sf "${BASE_URL}/api/troubleshooting" | grep -q "ROOT CAUSE IDENTIFIED"
curl -sf "${BASE_URL}/api/vsat" | grep -q "VSAT NETWORK CREATED"
curl -sf "${BASE_URL}/api/dashboard" | grep -q "SATCOM NOC DASHBOARD"

echo "[5/5] Validating required POST workflows"

curl -sf -X POST "${BASE_URL}/api/link-budget/calculate" \
  -H "Content-Type: application/json" \
  -d '{"linkName":"Lab-Link-01","txPowerDbw":18.5,"txAntennaGainDbi":43.2,"implementationLossDb":1.2,"slantRangeKm":38500,"frequencyGhz":14.0,"gOverTDbPerK":15.5,"noiseTemperatureK":320,"requiredCnDb":10.5}' | grep -q "LINK BUDGET CALCULATED"

curl -sf -X POST "${BASE_URL}/api/frequency-plan/generate" \
  -H "Content-Type: application/json" \
  -d '{"transponderSatelliteName":"INSAT-4A","transponderNumber":1,"channelPrefix":"LAB-CH","startUplinkFrequency":6.500,"startDownlinkFrequency":4.350,"channelBandwidth":2.0,"guardBand":0.2,"channelCount":3}' | grep -q "FREQUENCY PLAN GENERATED"

curl -sf -X POST "${BASE_URL}/api/alarms/simulate" \
  -H "Content-Type: application/json" \
  -d '{"linkName":"Hub-to-Site-5","cnDb":7.0,"ebNo":4.8,"ber":0.00032,"rxPowerDbm":-76.0,"lockStatus":"LOST"}' | grep -q "SATCOM ALARM GENERATED"

curl -sf -X POST "${BASE_URL}/api/troubleshooting/analyze" \
  -H "Content-Type: application/json" \
  -d '{"linkName":"Hub-to-Site-5","cnDb":7.0,"ebNo":4.8,"ber":0.00032,"rxPowerDbm":-76.0,"lockStatus":"LOST"}' | grep -q "ROOT CAUSE IDENTIFIED"

echo "PASS: Day-19 Satellite Communication Systems validation completed successfully."
