from typing import Any

from src.memory.conversation_store import ConversationStore
from src.models.markers import MEMORY_UPDATED, TASK_COMPLETED, TOOL_EXECUTED
from src.planner.planner_service import PlannerService
from src.services.llm_service import MockLLMService
from src.tools.tool_registry import ToolRegistry


class AgentOrchestrator:
    """Coordinates reason -> plan -> tool execute -> memory update -> grounded response."""

    def __init__(
        self,
        planner: PlannerService,
        tool_registry: ToolRegistry,
        memory_store: ConversationStore,
        llm_service: MockLLMService,
    ) -> None:
        self._planner = planner
        self._tool_registry = tool_registry
        self._memory_store = memory_store
        self._llm_service = llm_service

    def run(self, session_id: str, query: str, context: dict[str, Any] | None = None) -> dict[str, Any]:
        self._memory_store.append(session_id, "user", query, {"context": context or {}})

        plan = self._planner.create_plan(query, context)
        tool_result = self._tool_registry.execute(plan.selected_tool, plan.steps[0].arguments)
        answer = self._llm_service.synthesize(query=query, plan_reason=plan.reason, tool_output=tool_result)

        self._memory_store.append(
            session_id,
            "assistant",
            answer,
            {"selected_tool": plan.selected_tool, "confidence": plan.confidence},
        )

        trace = [*plan.trace, TOOL_EXECUTED, MEMORY_UPDATED, TASK_COMPLETED]
        return {
            "session_id": session_id,
            "query": query,
            "plan": plan.model_dump(),
            "tool": {"name": plan.selected_tool, "result": tool_result},
            "response": answer,
            "trace": trace,
        }

    def history(self, session_id: str) -> list[dict[str, Any]]:
        return self._memory_store.history(session_id)
