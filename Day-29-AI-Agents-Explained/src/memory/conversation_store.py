from collections import defaultdict
from typing import Any

from src.config.settings import AppSettings
from src.utils.time_utils import current_timestamp


class ConversationStore:
    """In-memory deterministic conversation/session store."""

    def __init__(self, settings: AppSettings) -> None:
        self._settings = settings
        self._sessions: dict[str, list[dict[str, Any]]] = defaultdict(list)

    def append(
        self,
        session_id: str,
        role: str,
        content: str,
        metadata: dict[str, Any] | None = None,
    ) -> dict[str, Any]:
        entry = {
            "session_id": session_id,
            "role": role,
            "content": content,
            "metadata": metadata or {},
            "timestamp": current_timestamp(self._settings),
        }
        self._sessions[session_id].append(entry)
        return entry

    def history(self, session_id: str) -> list[dict[str, Any]]:
        return list(self._sessions.get(session_id, []))

    def session_count(self) -> int:
        return len(self._sessions)
