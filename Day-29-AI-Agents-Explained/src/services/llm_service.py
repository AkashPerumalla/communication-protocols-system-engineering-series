from typing import Any

from src.config.settings import AppSettings


class MockLLMService:
    """OpenAI-compatible abstraction with deterministic local behavior."""

    def __init__(self, settings: AppSettings) -> None:
        self._settings = settings

    def synthesize(self, query: str, plan_reason: str, tool_output: dict[str, Any]) -> str:
        del query
        return (
            "Grounded response generated from selected tool evidence. "
            f"Planner rationale: {plan_reason}. "
            f"Evidence keys: {', '.join(sorted(tool_output.keys())) if tool_output else 'none'}."
        )
