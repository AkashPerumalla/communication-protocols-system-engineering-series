#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${ROOT_DIR}"

PORT=8093
BASE_URL="http://localhost:${PORT}"
APP_LOG="/tmp/day28_mcp_api.log"
APP_PID=""
UV_BIN="uv"

if ! command -v uv >/dev/null 2>&1; then
  UV_BIN="${HOME}/Library/Python/3.11/bin/uv"
fi

if [[ ! -x "${UV_BIN}" ]] && [[ "${UV_BIN}" != "uv" ]]; then
  echo "uv executable not found. Install uv or add it to PATH." >&2
  exit 1
fi

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

echo "[1/7] Sync dependencies with uv"
"${UV_BIN}" sync >/dev/null

echo "[2/7] Run pytest suite"
"${UV_BIN}" run pytest

echo "[3/7] Start FastAPI service"
"${UV_BIN}" run uvicorn src.main:app --host 0.0.0.0 --port "${PORT}" >"${APP_LOG}" 2>&1 &
APP_PID=$!

echo "[4/7] Wait for health endpoint"
for _ in {1..60}; do
  if curl -sf "${BASE_URL}/health" >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

echo "[5/7] Validate markers from REST APIs"
HEALTH="$(curl -sf "${BASE_URL}/health")"
TOOLS="$(curl -sf "${BASE_URL}/tools")"
QUERY="$(curl -sf -X POST "${BASE_URL}/query" -H 'Content-Type: application/json' -d '{"query":"platform status","filters":{}}')"
TOOL="$(curl -sf -X POST "${BASE_URL}/tool/system_info" -H 'Content-Type: application/json' -d '{"arguments":{}}')"

grep -q 'SYSTEM_HEALTHY' <<<"${HEALTH}"
grep -q 'API_SUCCESS' <<<"${TOOLS}"
grep -q 'QUERY_COMPLETED' <<<"${QUERY}"
grep -q 'TOOL_EXECUTED' <<<"${TOOL}"

echo "[6/7] Validate MCP stdio server/client markers"
MCP_READY_RESPONSE="$(printf '{"command":"ready"}\n' | "${UV_BIN}" run python -m src.mcp_server.stdio_entry | head -n 1)"
grep -q 'MCP_READY' <<<"${MCP_READY_RESPONSE}"
grep -q 'SERVER_RUNNING' <<<"${MCP_READY_RESPONSE}"

MCP_TOOL_RESPONSE="$(printf '{"tool":"system_info","arguments":{}}\n' | "${UV_BIN}" run python -m src.mcp_server.stdio_entry | head -n 1)"
grep -q 'TOOL_EXECUTED' <<<"${MCP_TOOL_RESPONSE}"

MCP_CLIENT_RESPONSE="$("${UV_BIN}" run python - <<'PY'
from pathlib import Path
from src.mcp_client.client import MCPClient
print(MCPClient(Path('.')).connect())
PY
)"
grep -q 'CLIENT_CONNECTED' <<<"${MCP_CLIENT_RESPONSE}"

echo "[7/7] PASS: Day-28 MCP platform validations succeeded"
