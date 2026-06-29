from typing import Any

from src.config.settings import AppSettings
from src.exceptions.custom_exceptions import DatabaseUnavailableError


class PostgresService:
    def __init__(self, settings: AppSettings) -> None:
        self._settings = settings

    def query(self, sql: str) -> dict[str, Any]:
        if not sql.strip():
            raise DatabaseUnavailableError("SQL query cannot be empty")
        if not self._settings.postgres_enabled:
            return {
                "mode": "mock",
                "sql": sql,
                "rows": [{"id": 1, "name": "fallback-result"}],
                "row_count": 1,
            }

        try:
            import psycopg
        except ImportError as exc:
            raise DatabaseUnavailableError("psycopg is not available") from exc

        with psycopg.connect(self._settings.postgres_dsn) as conn:
            with conn.cursor() as cur:
                cur.execute(sql)
                if cur.description is None:
                    return {"mode": "postgres", "sql": sql, "rows": [], "row_count": 0}
                columns = [col.name for col in cur.description]
                rows = [dict(zip(columns, row)) for row in cur.fetchall()]
                return {"mode": "postgres", "sql": sql, "rows": rows, "row_count": len(rows)}

    def status(self) -> dict[str, Any]:
        return {"postgres_enabled": self._settings.postgres_enabled, "dsn": self._settings.postgres_dsn}
