import re

from src.exceptions.custom_exceptions import ValidationError

_READ_ONLY_PATTERN = re.compile(r"^\s*(SELECT|WITH|EXPLAIN)\b", re.IGNORECASE)
_FORBIDDEN_PATTERN = re.compile(
    r"\b(INSERT|UPDATE|DELETE|DROP|ALTER|TRUNCATE|CREATE|GRANT|REVOKE|CALL|DO)\b",
    re.IGNORECASE,
)


def validate_read_only_sql(sql: str) -> str:
    normalized = sql.strip().rstrip(";")
    if not normalized:
        raise ValidationError("SQL query cannot be empty")
    if not _READ_ONLY_PATTERN.match(normalized):
        raise ValidationError("Only read-only SQL statements are allowed")
    if _FORBIDDEN_PATTERN.search(normalized):
        raise ValidationError("SQL contains forbidden write/DDL operations")
    return normalized
