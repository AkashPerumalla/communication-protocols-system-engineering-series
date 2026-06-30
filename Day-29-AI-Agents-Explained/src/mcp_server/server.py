import json
from typing import Any

from src.models.envelope import build_envelope
from src.models.markers import AGENT_READY, SYSTEM_HEALTHY, TOOL_EXECUTED
from src.services.container import ServiceContainer
from src.utils.time_utils import current_timestamp

try:
    from fastmcp import FastMCP
except ImportError:
    FastMCP = None


class MCPServer:
    def __init__(self, container: ServiceContainer) -> None:
        self._container = container
        self._server = FastMCP(container.settings.mcp_server_name) if FastMCP else None
        self._register_with_fastmcp_if_available()

    def _register_with_fastmcp_if_available(self) -> None:
        if not self._server:
            return
        for tool in self._container.tool_registry.list_tools():
            name = tool["name"]

            def _handler(arguments: dict[str, Any], tool_name: str = name) -> dict[str, Any]:
                return self._container.tool_registry.execute(tool_name, arguments)

            self._server.tool(name=name, description=tool["description"])(_handler)

    def readiness(self) -> dict[str, Any]:
        return {
            "marker": AGENT_READY,
            "status": SYSTEM_HEALTHY,
            "transport": self._container.settings.mcp_transport,
        }

    def execute_tool(self, name: str, arguments: dict[str, Any]) -> dict[str, Any]:
        result = self._container.tool_registry.execute(name, arguments)
        response = build_envelope(
            True,
            TOOL_EXECUTED,
            "MCP tool executed",
            {"tool": name, "result": result},
            current_timestamp(self._container.settings),
        )
        return response.model_dump()

    def run_stdio_line(self, line: str) -> str:
        payload = json.loads(line)
        if payload.get("command") == "ready":
            return json.dumps(self.readiness(), sort_keys=True)
        tool = payload.get("tool", "")
        arguments = payload.get("arguments", {})
        return json.dumps(self.execute_tool(tool, arguments), sort_keys=True)

    def run_stdio(self) -> None:
        import sys

        for line in sys.stdin:
            content = line.strip()
            if not content:
                continue
            sys.stdout.write(self.run_stdio_line(content) + "\n")
            sys.stdout.flush()
