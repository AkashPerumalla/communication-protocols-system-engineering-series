from functools import lru_cache
from pathlib import Path

from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict


class AppSettings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", env_file_encoding="utf-8", case_sensitive=False)

    app_name: str = "Day-29 Enterprise AI Agent Platform"
    app_env: str = "dev"
    app_host: str = "0.0.0.0"
    app_port: int = 8094
    log_level: str = "INFO"
    deterministic_mode: bool = True
    fixed_timestamp: str = "2026-06-30T00:00:00+00:00"
    request_timeout_seconds: float = 3.0
    sample_data_file: Path = Field(default_factory=lambda: Path("data/samples/platform_data.json"))
    log_file: Path = Field(default_factory=lambda: Path("data/logs/platform.log"))
    knowledge_base_file: Path = Field(default_factory=lambda: Path("README.md"))

    postgres_enabled: bool = False
    postgres_dsn: str = "postgresql://postgres:postgres@localhost:5432/day29_agents"

    mcp_server_name: str = "day29-agent-mcp-server"
    mcp_transport: str = "stdio"


@lru_cache(maxsize=1)
def get_settings() -> AppSettings:
    return AppSettings()
