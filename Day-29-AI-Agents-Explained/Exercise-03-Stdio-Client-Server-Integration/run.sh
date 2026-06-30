#!/usr/bin/env bash
set -euo pipefail
PYTHON_BIN="${PYTHON_BIN:-python}"
READY="$(printf '{"command":"ready"}\n' | "${PYTHON_BIN}" -m src.mcp_server.stdio_entry | head -n 1)"
TOOL="$(printf '{"tool":"system_info","arguments":{}}\n' | "${PYTHON_BIN}" -m src.mcp_server.stdio_entry | head -n 1)"
grep -q 'AGENT_READY' <<<"${READY}"
grep -q 'TOOL_EXECUTED' <<<"${TOOL}"
echo 'EXERCISE_03_PASS'
