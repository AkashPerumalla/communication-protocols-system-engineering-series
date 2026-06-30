import json
from pathlib import Path
from typing import Any


class DataRepository:
    def __init__(self, sample_file: Path) -> None:
        self._sample_file = sample_file
        self._data = self._load()

    def _load(self) -> dict[str, Any]:
        with self._sample_file.open("r", encoding="utf-8") as handle:
            return json.load(handle)

    def telemetry(self) -> list[dict[str, Any]]:
        return sorted(self._data.get("telemetry", []), key=lambda item: item["device_id"])

    def alarms(self) -> list[dict[str, Any]]:
        return sorted(self._data.get("alarms", []), key=lambda item: item["alarm_id"])

    def devices(self) -> list[dict[str, Any]]:
        return sorted(self._data.get("devices", []), key=lambda item: item["device_id"])

    def logs(self) -> list[dict[str, Any]]:
        return sorted(self._data.get("logs", []), key=lambda item: item["message"])
