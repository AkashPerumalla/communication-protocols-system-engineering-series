from fastapi.testclient import TestClient

from app.main import create_app


def test_api_end_to_end_markers():
    app = create_app()
    client = TestClient(app)

    index = client.post("/api/documents/index", json={"rebuild": True})
    assert index.status_code == 200
    assert index.json()["marker"] == "VECTOR_DB_UPDATED"

    docs = client.get("/api/documents")
    assert docs.status_code == 200
    assert docs.json()["marker"] == "DOCUMENT_RETRIEVED"

    search = client.get("/api/search", params={"q": "SNMP", "top_k": 3})
    assert search.status_code == 200
    assert search.json()["marker"] == "SEARCH_COMPLETED"

    query = client.post(
        "/api/query",
        json={"question": "What does GETBULK do in SNMP?", "top_k": 3, "conversation_id": "itest"},
    )
    assert query.status_code == 200
    assert query.json()["marker"] == "QUERY_COMPLETED"
    assert len(query.json()["data"]["citations"]) > 0

    status = client.get("/api/platform/status")
    assert status.status_code == 200
    assert status.json()["marker"] == "PLATFORM_READY"

    health = client.get("/actuator/health")
    assert health.status_code == 200
    assert health.json()["marker"] == "RAG_READY"

    reset = client.delete("/api/vectorstore/reset")
    assert reset.status_code == 200
    assert reset.json()["marker"] == "VECTOR_DB_UPDATED"
