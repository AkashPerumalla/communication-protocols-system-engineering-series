from typing import Any

from pydantic import BaseModel, Field


class PlanStep(BaseModel):
    order: int = Field(ge=1)
    action: str
    tool_name: str
    arguments: dict[str, Any] = Field(default_factory=dict)


class ExecutionPlan(BaseModel):
    reason: str
    selected_tool: str
    confidence: float = Field(ge=0.0, le=1.0)
    steps: list[PlanStep]
    trace: list[str] = Field(default_factory=list)
