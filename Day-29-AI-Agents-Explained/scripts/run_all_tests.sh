#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${ROOT_DIR}"

PORT=8094
BASE_URL="http://localhost:${PORT}"
APP_LOG="/tmp/day29_agent_api.log"
APP_PID=""
UV_BIN="uv"
PYTHON_BIN="python"

if ! command -v uv >/dev/null 2>&1; then
  UV_BIN="${HOME}/Library/Python/3.11/bin/uv"
fi

if [[ ! -x "${UV_BIN}" ]] && [[ "${UV_BIN}" != "uv" ]]; then
  UV_BIN=""
fi

if [[ -z "${UV_BIN}" ]]; then
  if ! command -v python >/dev/null 2>&1; then
    echo "Neither uv nor python found on PATH." >&2
    exit 1
  fi
  PYTHON_BIN="python"
fi

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

fetch_json() {
  local method="$1"
  local url="$2"
  local payload="${3:-}"
  local attempt
  local response

  for attempt in {1..10}; do
    if [[ -n "${payload}" ]]; then
      response="$(curl -sf -X "${method}" "${url}" -H 'Content-Type: application/json' -d "${payload}" 2>/dev/null)" && {
        printf '%s' "${response}"
        return 0
      }
    else
      response="$(curl -sf -X "${method}" "${url}" 2>/dev/null)" && {
        printf '%s' "${response}"
        return 0
      }
    fi
    sleep 1
  done

  return 1
}

echo "[1/7] Sync dependencies"
if [[ -n "${UV_BIN}" ]]; then
  "${UV_BIN}" sync >/dev/null
else
  "${PYTHON_BIN}" -m pip install -e . pytest pytest-cov >/dev/null
fi

echo "[2/7] Run pytest suite"
if [[ -n "${UV_BIN}" ]]; then
  "${UV_BIN}" run pytest --cov=src --cov-fail-under=75
else
  "${PYTHON_BIN}" -m pytest --cov=src --cov-fail-under=75
fi

echo "[3/7] Start FastAPI service"
if [[ -n "${UV_BIN}" ]]; then
  "${UV_BIN}" run uvicorn src.main:app --host 0.0.0.0 --port "${PORT}" >"${APP_LOG}" 2>&1 &
else
  "${PYTHON_BIN}" -m uvicorn src.main:app --host 0.0.0.0 --port "${PORT}" >"${APP_LOG}" 2>&1 &
fi
APP_PID=$!

echo "[4/7] Wait for health endpoint"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/health" >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

curl -sf "${BASE_URL}/health" >/dev/null 2>&1 || {
  echo "Service did not become ready in time." >&2
  exit 1
}

echo "[5/7] Validate markers from required REST APIs"
HEALTH="$(fetch_json GET "${BASE_URL}/health")"
STATUS="$(fetch_json GET "${BASE_URL}/platform/status")"
TOOLS="$(fetch_json GET "${BASE_URL}/agent/tools")"
CHAT="$(fetch_json POST "${BASE_URL}/agent/chat" '{"query":"show platform status","session_id":"run-script","context":{}}')"
RUN="$(fetch_json POST "${BASE_URL}/agent/run" '{"task":"lookup telemetry SAT-HUB-001","session_id":"run-script","inputs":{"device_id":"SAT-HUB-001"}}')"
HISTORY="$(fetch_json GET "${BASE_URL}/agent/history?session_id=run-script")"
grep -q 'SYSTEM_HEALTHY' <<<"${HEALTH}"
grep -q 'API_SUCCESS' <<<"${STATUS}"
grep -q 'AGENT_READY' <<<"${TOOLS}"
grep -q 'TASK_COMPLETED' <<<"${CHAT}"
grep -q 'TASK_COMPLETED' <<<"${RUN}"
grep -q 'MEMORY_UPDATED' <<<"${HISTORY}" || grep -q 'API_SUCCESS' <<<"${HISTORY}"

echo "[6/7] Validate agent workflow and tool execution markers"
grep -q 'PLAN_CREATED' <<<"${CHAT}"
grep -q 'TOOL_SELECTED' <<<"${CHAT}"
grep -q 'TOOL_EXECUTED' <<<"${CHAT}"
grep -q 'TASK_COMPLETED' <<<"${CHAT}"

echo "[7/7] Validate MCP stdio bridge markers"
if [[ -n "${UV_BIN}" ]]; then
  MCP_READY_RESPONSE="$(printf '{"command":"ready"}\n' | "${UV_BIN}" run python -m src.mcp_server.stdio_entry | head -n 1)"
  MCP_TOOL_RESPONSE="$(printf '{"tool":"system_info","arguments":{}}\n' | "${UV_BIN}" run python -m src.mcp_server.stdio_entry | head -n 1)"
else
  MCP_READY_RESPONSE="$(printf '{"command":"ready"}\n' | "${PYTHON_BIN}" -m src.mcp_server.stdio_entry | head -n 1)"
  MCP_TOOL_RESPONSE="$(printf '{"tool":"system_info","arguments":{}}\n' | "${PYTHON_BIN}" -m src.mcp_server.stdio_entry | head -n 1)"
fi
grep -q 'AGENT_READY' <<<"${MCP_READY_RESPONSE}"
grep -q 'SYSTEM_HEALTHY' <<<"${MCP_READY_RESPONSE}"
grep -q 'TOOL_EXECUTED' <<<"${MCP_TOOL_RESPONSE}"

echo "PASS: All tests passed"
