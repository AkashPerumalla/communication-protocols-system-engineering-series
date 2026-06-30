#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8094}"
RESP="$(curl -sf -X POST "${BASE_URL}/agent/chat" -H 'Content-Type: application/json' -d '{"query":"show platform status","session_id":"ex2","context":{}}')"
grep -q 'PLAN_CREATED' <<<"${RESP}"
grep -q 'TOOL_SELECTED' <<<"${RESP}"
grep -q 'TOOL_EXECUTED' <<<"${RESP}"
echo 'EXERCISE_02_PASS'
