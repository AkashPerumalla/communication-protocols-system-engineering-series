from typing import Any

from src.models.markers import PLAN_CREATED, TOOL_SELECTED
from src.planner.planner_models import ExecutionPlan, PlanStep


class PlannerService:
    """Deterministic planner that maps operator intent to the best available tool."""

    def create_plan(self, query: str, context: dict[str, Any] | None = None) -> ExecutionPlan:
        lowered = query.lower()
        args = dict(context or {})

        selected_tool = "system_info"
        reason = "Default operational context for uncategorized query."
        confidence = 0.64

        if "platform" in lowered and "status" in lowered:
            selected_tool = "platform_status"
            reason = "Platform health summary requested."
            confidence = 0.95
        elif "telemetry" in lowered:
            selected_tool = "telemetry_lookup"
            reason = "Telemetry metric retrieval requested."
            confidence = 0.93
        elif "alarm" in lowered:
            selected_tool = "alarm_lookup"
            reason = "Alarm state inspection requested."
            confidence = 0.92
        elif "device" in lowered and ("status" in lowered or "state" in lowered):
            selected_tool = "device_status"
            reason = "Device state verification requested."
            confidence = 0.91
        elif "log" in lowered:
            selected_tool = "log_search"
            args.setdefault("query", query)
            reason = "Log pattern lookup requested."
            confidence = 0.89
        elif "knowledge" in lowered or "kb" in lowered:
            selected_tool = "knowledge_search"
            args.setdefault("query", query)
            reason = "Knowledge base lookup requested."
            confidence = 0.88
        elif "postgres" in lowered or "sql" in lowered or "database" in lowered:
            selected_tool = "postgresql_query"
            args.setdefault("sql", "SELECT 1 AS ok")
            reason = "Read-only database inspection requested."
            confidence = 0.85
        elif "file" in lowered and ("search" in lowered or "read" in lowered):
            selected_tool = "file_search_read"
            args.setdefault("path", "README.md")
            args.setdefault("query", "Agent")
            reason = "Repository artifact lookup requested."
            confidence = 0.84
        elif "rest" in lowered or "http" in lowered or "api" in lowered:
            selected_tool = "rest_api_client"
            args.setdefault("url", "https://example.com")
            args.setdefault("method", "GET")
            reason = "External service check requested."
            confidence = 0.8
        elif "system" in lowered or "cpu" in lowered or "memory" in lowered:
            selected_tool = "system_info"
            reason = "Host resource diagnostics requested."
            confidence = 0.9

        steps = [
            PlanStep(
                order=1,
                action="Execute selected tool for grounded evidence",
                tool_name=selected_tool,
                arguments=args,
            )
        ]

        return ExecutionPlan(
            reason=reason,
            selected_tool=selected_tool,
            confidence=confidence,
            steps=steps,
            trace=[PLAN_CREATED, TOOL_SELECTED],
        )
