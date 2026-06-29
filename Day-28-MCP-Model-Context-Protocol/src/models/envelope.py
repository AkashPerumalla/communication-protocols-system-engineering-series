from datetime import datetime, timezone
from typing import Any

from pydantic import BaseModel, Field


class ResponseEnvelope(BaseModel):
    success: bool
    marker: str
    message: str
    data: Any = None
    timestamp: str = Field(default_factory=lambda: datetime.now(timezone.utc).isoformat())


def build_envelope(
    success: bool,
    marker: str,
    message: str,
    data: Any,
    timestamp: str | None = None,
) -> ResponseEnvelope:
    return ResponseEnvelope(
        success=success,
        marker=marker,
        message=message,
        data=data,
        timestamp=timestamp or datetime.now(timezone.utc).isoformat(),
    )
