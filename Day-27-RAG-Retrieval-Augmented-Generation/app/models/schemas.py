from typing import Any

from pydantic import BaseModel, Field


class IndexRequest(BaseModel):
    rebuild: bool = False


class QueryRequest(BaseModel):
    question: str
    top_k: int | None = None
    metadata_filter: dict[str, Any] | None = None
    conversation_id: str = "default"


class SearchRequest(BaseModel):
    query: str
    top_k: int | None = None
    metadata_filter: dict[str, Any] | None = None


class Citation(BaseModel):
    source: str
    chunk_id: str
    score: float
    metadata: dict[str, Any] = Field(default_factory=dict)


class QueryResponse(BaseModel):
    question: str
    answer: str
    citations: list[Citation]
    retrieved_context: list[dict[str, Any]]
    retrieval_latency_ms: float
