#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8094}"
T1="$(curl -sf -X POST "${BASE_URL}/agent/run" -H 'Content-Type: application/json' -d '{"task":"telemetry for SAT-HUB-001","session_id":"ex6","inputs":{"device_id":"SAT-HUB-001"}}')"
T2="$(curl -sf -X POST "${BASE_URL}/agent/run" -H 'Content-Type: application/json' -d '{"task":"alarm summary","session_id":"ex6","inputs":{}}')"
T3="$(curl -sf -X POST "${BASE_URL}/agent/run" -H 'Content-Type: application/json' -d '{"task":"log search latency","session_id":"ex6","inputs":{"query":"latency"}}')"
grep -q 'TASK_COMPLETED' <<<"${T1}"
grep -q 'TASK_COMPLETED' <<<"${T2}"
grep -q 'TASK_COMPLETED' <<<"${T3}"
echo 'EXERCISE_06_PASS'
