from typing import Any

from pydantic import BaseModel, Field


class AgentChatRequest(BaseModel):
    query: str = Field(min_length=1, max_length=800)
    session_id: str = Field(default="default", min_length=1, max_length=100)
    context: dict[str, Any] = Field(default_factory=dict)


class AgentRunRequest(BaseModel):
    task: str = Field(min_length=1, max_length=800)
    session_id: str = Field(default="default", min_length=1, max_length=100)
    inputs: dict[str, Any] = Field(default_factory=dict)


class HistoryQuery(BaseModel):
    session_id: str = Field(default="default", min_length=1, max_length=100)


class ToolDescriptor(BaseModel):
    name: str
    description: str
    enabled: bool


class AgentResult(BaseModel):
    session_id: str
    query: str
    plan: dict[str, Any]
    tool: dict[str, Any]
    response: str
    trace: list[str]
