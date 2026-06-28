from functools import lru_cache
from pathlib import Path

from pydantic import Field
from pydantic_settings import BaseSettings, SettingsConfigDict


class AppSettings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", env_file_encoding="utf-8", case_sensitive=False)

    app_name: str = "Day-27 Enterprise RAG Knowledge Assistant"
    app_env: str = "dev"
    app_host: str = "0.0.0.0"
    app_port: int = 8092
    log_level: str = "INFO"

    openai_api_key: str | None = None
    openai_model: str = "gpt-4o-mini"
    use_mock_llm: bool = True

    embedding_model_name: str = "sentence-transformers/all-MiniLM-L6-v2"
    use_mock_embeddings: bool = False

    chunk_size: int = 900
    chunk_overlap: int = 120
    retrieval_top_k: int = 4

    documents_dir: Path = Field(default_factory=lambda: Path("data/documents"))
    chromadb_dir: Path = Field(default_factory=lambda: Path("data/chromadb"))
    collection_name: str = "enterprise_knowledge_base"

    allowed_extensions: str = ".pdf,.docx,.txt,.md"


@lru_cache(maxsize=1)
def get_settings() -> AppSettings:
    return AppSettings()
