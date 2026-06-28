from datetime import datetime, timezone
from typing import Any

from pydantic import BaseModel, Field


class ApiEnvelope(BaseModel):
    success: bool
    marker: str
    message: str = ""
    data: Any = Field(default_factory=dict)
    timestamp: str


def envelope(success: bool, marker: str, message: str = "", data: Any = None) -> ApiEnvelope:
    payload = {} if data is None else data
    return ApiEnvelope(
        success=success,
        marker=marker,
        message=message,
        data=payload,
        timestamp=datetime.now(timezone.utc).isoformat(),
    )
