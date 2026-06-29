from functools import lru_cache
from pathlib import Path

from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict


class AppSettings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", env_file_encoding="utf-8", case_sensitive=False)

    app_name: str = "Day-28 Enterprise MCP Platform"
    app_env: str = "dev"
    app_host: str = "0.0.0.0"
    app_port: int = 8093
    log_level: str = "INFO"
    deterministic_mode: bool = True
    fixed_timestamp: str = "2026-06-29T00:00:00+00:00"
    request_timeout_seconds: float = 3.0
    sample_data_file: Path = Field(default_factory=lambda: Path("data/samples/platform_data.json"))
    log_file: Path = Field(default_factory=lambda: Path("data/logs/platform.log"))

    postgres_enabled: bool = False
    postgres_dsn: str = "postgresql://postgres:postgres@localhost:5432/day28_mcp"

    mcp_server_name: str = "day28-mcp-server"
    mcp_transport: str = "stdio"


@lru_cache(maxsize=1)
def get_settings() -> AppSettings:
    return AppSettings()
