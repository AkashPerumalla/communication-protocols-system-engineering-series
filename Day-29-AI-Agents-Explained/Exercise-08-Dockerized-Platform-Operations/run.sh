#!/usr/bin/env bash
set -euo pipefail
docker compose up --build -d
trap 'docker compose down' EXIT
sleep 3
curl -sf "http://localhost:8094/health" | grep -q 'SYSTEM_HEALTHY'
curl -sf -X POST "http://localhost:8094/agent/chat" -H 'Content-Type: application/json' -d '{"query":"show platform status","session_id":"ex8","context":{}}' | grep -q 'TASK_COMPLETED'
echo 'EXERCISE_08_PASS'
