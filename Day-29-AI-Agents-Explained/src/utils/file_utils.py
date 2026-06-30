from pathlib import Path

from src.exceptions.custom_exceptions import ValidationError


def ensure_safe_path(base: Path, raw_path: str) -> Path:
    candidate = (base / raw_path).resolve()
    if not str(candidate).startswith(str(base.resolve())):
        raise ValidationError("Path traversal detected")
    return candidate
