import json
import logging
from datetime import datetime, timezone

from src.config.settings import AppSettings


class JsonFormatter(logging.Formatter):
    def format(self, record: logging.LogRecord) -> str:
        payload = {
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "level": record.levelname,
            "logger": record.name,
            "message": record.getMessage(),
        }
        if hasattr(record, "request_id"):
            payload["request_id"] = record.request_id
        return json.dumps(payload, separators=(",", ":"), sort_keys=True)


def configure_logging(settings: AppSettings) -> None:
    logging.basicConfig(level=getattr(logging, settings.log_level.upper(), logging.INFO))
    root_logger = logging.getLogger()
    for handler in root_logger.handlers:
        handler.setFormatter(JsonFormatter())


def get_logger(name: str) -> logging.Logger:
    return logging.getLogger(name)
