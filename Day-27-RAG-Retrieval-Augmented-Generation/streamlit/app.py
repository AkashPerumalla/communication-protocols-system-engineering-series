import requests
import streamlit as st


BASE_URL = st.sidebar.text_input("FastAPI Base URL", "http://localhost:8092")

st.title("Enterprise RAG Knowledge Assistant")
st.caption("NOC / Telecom / SatCom / IoT documentation assistant with citations")

st.subheader("Document Upload")
uploaded = st.file_uploader("Upload PDF, DOCX, TXT, or MD", type=["pdf", "docx", "txt", "md"])
if st.button("Upload Document") and uploaded is not None:
    files = {"file": (uploaded.name, uploaded.getvalue(), uploaded.type or "application/octet-stream")}
    resp = requests.post(f"{BASE_URL}/api/documents/upload", files=files, timeout=60)
    st.json(resp.json())

if st.button("Index Documents"):
    resp = requests.post(f"{BASE_URL}/api/documents/index", json={"rebuild": False}, timeout=120)
    st.json(resp.json())

st.subheader("Ask Engineering Question")
question = st.text_area("Question", "What is the recommended alarm acknowledgement SLA?")
top_k = st.slider("Top-K", min_value=1, max_value=10, value=4)
if st.button("Query RAG"):
    resp = requests.post(
        f"{BASE_URL}/api/query",
        json={"question": question, "top_k": top_k, "conversation_id": "streamlit"},
        timeout=120,
    )
    data = resp.json()
    st.json(data)
    payload = data.get("data", {})
    st.markdown("### Answer")
    st.write(payload.get("answer", ""))
    st.markdown("### Retrieval Latency")
    st.write(f"{payload.get('retrieval_latency_ms', 0)} ms")

    st.markdown("### Retrieved Context")
    for item in payload.get("retrieved_context", []):
        st.write({"source": item.get("source"), "score": item.get("score")})
        st.code(item.get("text", "")[:500])

    st.markdown("### Citations")
    st.table(payload.get("citations", []))

if st.button("Show Indexed Documents"):
    resp = requests.get(f"{BASE_URL}/api/documents", timeout=60)
    st.json(resp.json())
