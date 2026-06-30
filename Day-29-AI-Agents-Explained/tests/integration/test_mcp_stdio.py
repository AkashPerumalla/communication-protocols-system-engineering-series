from pathlib import Path

from src.mcp_client.client import MCPClient
from src.models.markers import AGENT_READY, TOOL_EXECUTED


def test_mcp_client_connects() -> None:
    client = MCPClient(Path(__file__).resolve().parents[2])
    response = client.connect()
    assert response["marker"] == AGENT_READY


def test_mcp_client_executes_tool() -> None:
    client = MCPClient(Path(__file__).resolve().parents[2])
    result = client.execute_tool("system_info", {})
    assert result["marker"] == TOOL_EXECUTED
