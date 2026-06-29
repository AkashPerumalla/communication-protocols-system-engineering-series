from collections.abc import Callable
from typing import Any

from src.exceptions.custom_exceptions import ToolNotFoundError

ToolHandler = Callable[[dict[str, Any]], dict[str, Any]]


class ToolRegistryService:
    def __init__(self) -> None:
        self._tools: dict[str, tuple[str, ToolHandler]] = {}

    def register(self, name: str, description: str, handler: ToolHandler) -> None:
        self._tools[name] = (description, handler)

    def list_tools(self) -> list[dict[str, str]]:
        return [{"name": name, "description": meta[0]} for name, meta in sorted(self._tools.items())]

    def execute(self, name: str, args: dict[str, Any]) -> dict[str, Any]:
        if name not in self._tools:
            raise ToolNotFoundError(f"Unknown tool: {name}")
        return self._tools[name][1](args)
