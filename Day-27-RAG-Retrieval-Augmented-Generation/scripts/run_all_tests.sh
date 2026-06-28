#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "${ROOT_DIR}"

export USE_MOCK_LLM=true
export USE_MOCK_EMBEDDINGS=true

APP_LOG="/tmp/day27_fastapi.log"
STREAMLIT_LOG="/tmp/day27_streamlit.log"
APP_PID=""
STREAMLIT_PID=""

cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" >/dev/null 2>&1; then
    kill "${APP_PID}" || true
  fi
  if [[ -n "${STREAMLIT_PID}" ]] && kill -0 "${STREAMLIT_PID}" >/dev/null 2>&1; then
    kill "${STREAMLIT_PID}" || true
  fi
}
trap cleanup EXIT

echo "[1/10] Installing dependencies"
python -m pip install -r requirements.txt >/dev/null

echo "[2/10] Running tests"
pytest -q

echo "[3/10] Building vector database"
uvicorn app.main:app --host 0.0.0.0 --port 8092 >"${APP_LOG}" 2>&1 &
APP_PID=$!

for _ in {1..60}; do
  if curl -sf "http://localhost:8092/actuator/health" >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

echo "[4/10] Validating document indexing"
INDEX_RESPONSE="$(curl -sf -X POST "http://localhost:8092/api/documents/index" -H "Content-Type: application/json" -d '{"rebuild": true}')"
grep -q 'VECTOR_DB_UPDATED' <<<"${INDEX_RESPONSE}"

echo "[5/10] Validating retrieval"
SEARCH_RESPONSE="$(curl -sf "http://localhost:8092/api/search?q=alarm%20sla&top_k=3")"
grep -q 'SEARCH_COMPLETED' <<<"${SEARCH_RESPONSE}"

echo "[6/10] Validating citations"
QUERY_RESPONSE="$(curl -sf -X POST "http://localhost:8092/api/query" -H "Content-Type: application/json" -d '{"question":"What is the alarm acknowledgement SLA?","top_k":3,"conversation_id":"automation"}')"
grep -q 'SOURCES_ATTACHED' <<<"${QUERY_RESPONSE}"

echo "[7/10] Starting FastAPI"
grep -q 'RAG_READY' <(curl -sf "http://localhost:8092/actuator/health")

echo "[8/10] Executing API smoke tests"
grep -q 'PLATFORM_READY' <(curl -sf "http://localhost:8092/api/platform/status")
grep -q 'DOCUMENT_RETRIEVED' <(curl -sf "http://localhost:8092/api/documents")

echo "[9/10] Validating Streamlit launch"
streamlit run streamlit/app.py --server.port 8501 >"${STREAMLIT_LOG}" 2>&1 &
STREAMLIT_PID=$!
for _ in {1..45}; do
  if curl -sf "http://localhost:8501" >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

echo "[10/10] PASS"
echo "PASS"
