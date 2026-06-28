from app.config.settings import AppSettings
from app.services.vectorstore_service import VectorStoreService


class PlatformStatusService:
    def __init__(self, settings: AppSettings, vectorstore_service: VectorStoreService) -> None:
        self.settings = settings
        self.vectorstore_service = vectorstore_service

    def status(self) -> dict:
        return {
            "service": self.settings.app_name,
            "environment": self.settings.app_env,
            "embedding_model": self.settings.embedding_model_name,
            "llm_model": self.settings.openai_model,
            "mock_llm": self.settings.use_mock_llm,
            "vector_collection": self.settings.collection_name,
            "indexed_chunks": self.vectorstore_service.count(),
        }
