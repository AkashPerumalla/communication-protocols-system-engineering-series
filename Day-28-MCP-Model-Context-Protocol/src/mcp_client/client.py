import json
import subprocess
import sys
from pathlib import Path
from typing import Any

from src.models.markers import CLIENT_CONNECTED


class MCPClient:
    def __init__(self, project_root: Path) -> None:
        self._project_root = project_root

    def _run_exchange(self, payload: dict[str, Any]) -> dict[str, Any]:
        proc = subprocess.run(
            [sys.executable, "-m", "src.mcp_server.stdio_entry"],
            input=(json.dumps(payload) + "\n").encode("utf-8"),
            cwd=self._project_root,
            capture_output=True,
            check=True,
        )
        output = proc.stdout.decode("utf-8").strip().splitlines()[0]
        return json.loads(output)

    def connect(self) -> dict[str, str]:
        response = self._run_exchange({"command": "ready"})
        return {
            "marker": CLIENT_CONNECTED,
            "server_marker": response.get("marker", ""),
            "status": "connected",
        }

    def execute_tool(self, name: str, arguments: dict[str, Any]) -> dict[str, Any]:
        return self._run_exchange({"tool": name, "arguments": arguments})
