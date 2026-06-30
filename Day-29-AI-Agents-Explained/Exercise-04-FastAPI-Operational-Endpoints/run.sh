#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8094}"
curl -sf "${BASE_URL}/health" | grep -q 'SYSTEM_HEALTHY'
curl -sf "${BASE_URL}/platform/status" | grep -q 'API_SUCCESS'
curl -sf "${BASE_URL}/agent/tools" | grep -q 'AGENT_READY'
curl -sf -X POST "${BASE_URL}/agent/chat" -H 'Content-Type: application/json' -d '{"query":"show alarms","session_id":"ex4","context":{}}' | grep -q 'TASK_COMPLETED'
curl -sf -X POST "${BASE_URL}/agent/run" -H 'Content-Type: application/json' -d '{"task":"lookup telemetry","session_id":"ex4","inputs":{}}' | grep -q 'TASK_COMPLETED'
curl -sf "${BASE_URL}/agent/history?session_id=ex4" | grep -q 'API_SUCCESS'
echo 'EXERCISE_04_PASS'
