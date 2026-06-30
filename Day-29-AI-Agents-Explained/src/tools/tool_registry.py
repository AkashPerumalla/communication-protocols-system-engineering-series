from collections.abc import Callable
from dataclasses import dataclass
from typing import Any

from src.exceptions.custom_exceptions import ToolNotFoundError

ToolHandler = Callable[[dict[str, Any]], dict[str, Any]]


@dataclass(frozen=True)
class ToolSpec:
    name: str
    description: str
    enabled: bool


class ToolRegistry:
    def __init__(self) -> None:
        self._tools: dict[str, tuple[ToolSpec, ToolHandler]] = {}

    def register(self, name: str, description: str, handler: ToolHandler, enabled: bool = True) -> None:
        spec = ToolSpec(name=name, description=description, enabled=enabled)
        self._tools[name] = (spec, handler)

    def list_tools(self) -> list[dict[str, Any]]:
        return [
            {
                "name": spec.name,
                "description": spec.description,
                "enabled": spec.enabled,
            }
            for spec, _ in sorted(self._tools.values(), key=lambda item: item[0].name)
        ]

    def execute(self, name: str, args: dict[str, Any] | None = None) -> dict[str, Any]:
        if name not in self._tools:
            raise ToolNotFoundError(f"Unknown tool: {name}")
        spec, handler = self._tools[name]
        if not spec.enabled:
            return {"status": "degraded", "message": f"Tool {name} is disabled"}
        return handler(args or {})
