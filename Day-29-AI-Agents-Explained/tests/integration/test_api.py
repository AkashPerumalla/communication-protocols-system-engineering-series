from fastapi.testclient import TestClient

from src.models.markers import AGENT_READY, API_SUCCESS, SYSTEM_HEALTHY, TASK_COMPLETED


def test_health_endpoint(app) -> None:
    client = TestClient(app)
    response = client.get("/health")

    assert response.status_code == 200
    body = response.json()
    assert body["marker"] == SYSTEM_HEALTHY
    assert "x-request-id" in response.headers


def test_list_tools_endpoint(app) -> None:
    client = TestClient(app)
    response = client.get("/agent/tools")

    assert response.status_code == 200
    assert response.json()["marker"] == AGENT_READY
    tools = response.json()["data"]["tools"]
    assert any(tool["name"] == "system_info" for tool in tools)
    assert any(tool["name"] == "knowledge_search" for tool in tools)


def test_agent_chat_endpoint(app) -> None:
    client = TestClient(app)
    response = client.post(
        "/agent/chat",
        json={"query": "show platform status", "session_id": "s1", "context": {}},
    )

    assert response.status_code == 200
    body = response.json()
    assert body["marker"] == TASK_COMPLETED
    assert "PLAN_CREATED" in body["data"]["trace"]
    assert "TOOL_EXECUTED" in body["data"]["trace"]


def test_agent_run_and_history_endpoint(app) -> None:
    client = TestClient(app)
    response = client.post(
        "/agent/run",
        json={"task": "find telemetry for SAT-HUB-001", "session_id": "s-history", "inputs": {"device_id": "SAT-HUB-001"}},
    )
    assert response.status_code == 200
    assert response.json()["marker"] == TASK_COMPLETED

    second_response = client.post(
        "/agent/run",
        json={"task": "show platform status", "session_id": "s-history", "inputs": {}},
    )
    assert second_response.status_code == 200
    assert second_response.json()["marker"] == TASK_COMPLETED

    history_response = client.get("/agent/history", params={"session_id": "s-history"})
    assert history_response.status_code == 200
    assert history_response.json()["marker"] == API_SUCCESS
    assert len(history_response.json()["data"]["messages"]) >= 4


def test_platform_status_endpoint(app) -> None:
    client = TestClient(app)
    response = client.get("/platform/status")
    assert response.status_code == 200
    assert response.json()["marker"] == API_SUCCESS
