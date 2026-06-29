from pathlib import Path

from src.mcp_client.client import MCPClient
from src.models.markers import CLIENT_CONNECTED, TOOL_EXECUTED


def test_mcp_client_connects() -> None:
    client = MCPClient(Path("."))
    response = client.connect()
    assert response["marker"] == CLIENT_CONNECTED


def test_mcp_client_executes_tool() -> None:
    client = MCPClient(Path("."))
    result = client.execute_tool("system_info", {})
    assert result["marker"] == TOOL_EXECUTED
