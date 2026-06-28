import time
from typing import Any

from app.config.settings import AppSettings
from app.services.statistics_service import StatisticsService
from app.services.vectorstore_service import VectorStoreService


class RetrieverService:
    def __init__(
        self,
        settings: AppSettings,
        vectorstore_service: VectorStoreService,
        statistics_service: StatisticsService,
    ) -> None:
        self.settings = settings
        self.vectorstore_service = vectorstore_service
        self.statistics_service = statistics_service

    def retrieve(self, query: str, top_k: int | None = None, metadata_filter: dict[str, Any] | None = None) -> tuple[list[dict], float]:
        started = time.perf_counter()
        k = top_k or self.settings.retrieval_top_k
        docs = self.vectorstore_service.search(query=query, top_k=k, metadata_filter=metadata_filter)
        latency_ms = (time.perf_counter() - started) * 1000
        self.statistics_service.record_retrieval(latency_ms, len(docs))
        return docs, latency_ms
