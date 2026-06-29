from typing import Any

import httpx

from src.config.settings import AppSettings


class RestService:
    def __init__(self, settings: AppSettings) -> None:
        self._settings = settings

    def call(self, url: str, method: str = "GET") -> dict[str, Any]:
        if self._settings.deterministic_mode:
            return {
                "url": url,
                "method": method.upper(),
                "status_code": 200,
                "body": {"mock": True, "message": "deterministic response"},
            }

        with httpx.Client(timeout=self._settings.request_timeout_seconds) as client:
            response = client.request(method.upper(), url)
            body: Any
            try:
                body = response.json()
            except ValueError:
                body = response.text
            return {
                "url": url,
                "method": method.upper(),
                "status_code": response.status_code,
                "body": body,
            }
