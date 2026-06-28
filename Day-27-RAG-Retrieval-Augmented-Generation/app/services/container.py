from app.config.settings import AppSettings, get_settings
from app.services.chunking_service import ChunkingService
from app.services.citation_service import CitationService
from app.services.document_loader_service import DocumentLoaderService
from app.services.embedding_service import EmbeddingService
from app.services.llm_service import LLMService
from app.services.platform_status_service import PlatformStatusService
from app.services.rag_pipeline_service import RagPipelineService
from app.services.retriever_service import RetrieverService
from app.services.statistics_service import StatisticsService
from app.services.vectorstore_service import VectorStoreService


class ServiceContainer:
    def __init__(self, settings: AppSettings | None = None) -> None:
        self.settings = settings or get_settings()

        self.statistics_service = StatisticsService()
        self.document_loader_service = DocumentLoaderService(self.settings)
        self.chunking_service = ChunkingService(self.settings)
        self.embedding_service = EmbeddingService(self.settings)
        self.vectorstore_service = VectorStoreService(self.settings, self.embedding_service)
        self.retriever_service = RetrieverService(self.settings, self.vectorstore_service, self.statistics_service)
        self.citation_service = CitationService()
        self.llm_service = LLMService(self.settings)
        self.rag_pipeline_service = RagPipelineService(
            self.settings,
            self.retriever_service,
            self.llm_service,
            self.citation_service,
            self.statistics_service,
        )
        self.platform_status_service = PlatformStatusService(self.settings, self.vectorstore_service)
