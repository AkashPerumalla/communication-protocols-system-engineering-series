from fastapi.testclient import TestClient

from src.models.markers import QUERY_COMPLETED, SYSTEM_HEALTHY, TOOL_EXECUTED


def test_health_endpoint(app) -> None:
    client = TestClient(app)
    response = client.get("/health")

    assert response.status_code == 200
    body = response.json()
    assert body["marker"] == SYSTEM_HEALTHY


def test_list_tools_endpoint(app) -> None:
    client = TestClient(app)
    response = client.get("/tools")

    assert response.status_code == 200
    tools = response.json()["data"]["tools"]
    assert any(tool["name"] == "system_info" for tool in tools)


def test_query_endpoint(app) -> None:
    client = TestClient(app)
    response = client.post("/query", json={"query": "show platform status", "filters": {}})

    assert response.status_code == 200
    assert response.json()["marker"] == QUERY_COMPLETED


def test_tool_endpoint(app) -> None:
    client = TestClient(app)
    response = client.post("/tool/system_info", json={"arguments": {}})

    assert response.status_code == 200
    assert response.json()["marker"] == TOOL_EXECUTED
