from typing import Any

from pydantic import BaseModel, Field


class QueryRequest(BaseModel):
    query: str = Field(min_length=1, max_length=500)
    filters: dict[str, Any] = Field(default_factory=dict)


class ToolInvocationRequest(BaseModel):
    arguments: dict[str, Any] = Field(default_factory=dict)


class ToolDescriptor(BaseModel):
    name: str
    description: str


class ToolExecutionResult(BaseModel):
    tool_name: str
    result: dict[str, Any]
