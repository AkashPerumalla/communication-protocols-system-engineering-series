from collections import defaultdict

from app.config.settings import AppSettings
from app.models.schemas import QueryResponse
from app.services.citation_service import CitationService
from app.services.llm_service import LLMService
from app.services.retriever_service import RetrieverService
from app.services.statistics_service import StatisticsService


class RagPipelineService:
    def __init__(
        self,
        settings: AppSettings,
        retriever_service: RetrieverService,
        llm_service: LLMService,
        citation_service: CitationService,
        statistics_service: StatisticsService,
    ) -> None:
        self.settings = settings
        self.retriever_service = retriever_service
        self.llm_service = llm_service
        self.citation_service = citation_service
        self.statistics_service = statistics_service
        self.conversation_history: dict[str, list[dict]] = defaultdict(list)

    def answer(self, question: str, conversation_id: str, top_k: int | None, metadata_filter: dict | None) -> QueryResponse:
        retrieved, retrieval_latency_ms = self.retriever_service.retrieve(
            query=question,
            top_k=top_k,
            metadata_filter=metadata_filter,
        )
        answer = self.llm_service.generate_answer(question=question, retrieved_context=retrieved)
        citations = self.citation_service.build_citations(retrieved)

        self.conversation_history[conversation_id].append(
            {
                "question": question,
                "answer": answer,
                "citations": [c.model_dump() for c in citations],
            }
        )
        self.statistics_service.record_query()

        return QueryResponse(
            question=question,
            answer=answer,
            citations=citations,
            retrieved_context=retrieved,
            retrieval_latency_ms=round(retrieval_latency_ms, 3),
        )
