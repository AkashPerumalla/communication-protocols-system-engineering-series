from src.config.settings import AppSettings


def current_timestamp(settings: AppSettings) -> str:
    if settings.deterministic_mode:
        return settings.fixed_timestamp
    from datetime import datetime, timezone

    return datetime.now(timezone.utc).isoformat()
