from typing import Any

from src.services.tool_registry_service import ToolRegistryService


class QueryService:
    def __init__(self, tool_registry: ToolRegistryService) -> None:
        self._tool_registry = tool_registry

    def execute_query(self, query: str, filters: dict[str, Any]) -> dict[str, Any]:
        lowered = query.lower()
        if "health" in lowered or "status" in lowered:
            tool = "platform_status"
        elif "alarm" in lowered:
            tool = "alarm_lookup"
        elif "telemetry" in lowered:
            tool = "telemetry_lookup"
        else:
            tool = "system_info"
        return {
            "selected_tool": tool,
            "query": query,
            "filters": filters,
            "result": self._tool_registry.execute(tool, filters),
        }
