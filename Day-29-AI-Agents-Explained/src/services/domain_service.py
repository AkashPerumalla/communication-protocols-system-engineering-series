from typing import Any

from src.services.data_repository import DataRepository


class DomainService:
    def __init__(self, repository: DataRepository) -> None:
        self._repository = repository

    def telemetry_lookup(self, device_id: str | None = None) -> dict[str, Any]:
        records = self._repository.telemetry()
        if device_id:
            records = [item for item in records if item["device_id"] == device_id]
        return {"count": len(records), "items": records}

    def alarm_lookup(self, severity: str | None = None) -> dict[str, Any]:
        records = self._repository.alarms()
        if severity:
            records = [item for item in records if item["severity"] == severity.upper()]
        return {"count": len(records), "items": records}

    def device_status(self, device_id: str | None = None) -> dict[str, Any]:
        records = self._repository.devices()
        if device_id:
            records = [item for item in records if item["device_id"] == device_id]
        return {"count": len(records), "items": records}

    def log_search(self, query: str) -> dict[str, Any]:
        logs = self._repository.logs()
        filtered = [row for row in logs if query.lower() in row["message"].lower()]
        return {"count": len(filtered), "items": filtered}
