#!/usr/bin/env bash
set -euo pipefail
BASE_URL="${BASE_URL:-http://localhost:8094}"
curl -sf "${BASE_URL}/health" | grep -q 'SYSTEM_HEALTHY'
curl -sf "${BASE_URL}/agent/tools" | grep -q 'AGENT_READY'
echo 'EXERCISE_01_PASS'
