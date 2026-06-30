#!/usr/bin/env bash
set -euo pipefail
PYTHON_BIN="${PYTHON_BIN:-python}"
BASE_URL="${BASE_URL:-http://localhost:8094}"
"${PYTHON_BIN}" -m pytest -q
R1="$(curl -sf -X POST "${BASE_URL}/agent/chat" -H 'Content-Type: application/json' -d '{"query":"show platform status","session_id":"ex7","context":{}}')"
R2="$(curl -sf -X POST "${BASE_URL}/agent/chat" -H 'Content-Type: application/json' -d '{"query":"show platform status","session_id":"ex7","context":{}}')"
grep -q 'PLAN_CREATED' <<<"${R1}"
grep -q 'PLAN_CREATED' <<<"${R2}"
echo 'EXERCISE_07_PASS'
