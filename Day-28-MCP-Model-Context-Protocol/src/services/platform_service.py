from typing import Any

from src.models.markers import DATABASE_CONNECTED, SERVER_RUNNING, SYSTEM_HEALTHY
from src.services.postgres_service import PostgresService
from src.services.system_service import SystemService


class PlatformService:
    def __init__(self, system_service: SystemService, postgres_service: PostgresService) -> None:
        self._system_service = system_service
        self._postgres_service = postgres_service

    def health(self) -> dict[str, Any]:
        return {
            "status": "UP",
            "marker": SYSTEM_HEALTHY,
            "server": SERVER_RUNNING,
        }

    def platform_status(self) -> dict[str, Any]:
        db_status = self._postgres_service.status()
        payload = {
            "system": self._system_service.system_info(),
            "database": db_status,
            "database_marker": DATABASE_CONNECTED if db_status["postgres_enabled"] else "DATABASE_OPTIONAL",
        }
        return payload
