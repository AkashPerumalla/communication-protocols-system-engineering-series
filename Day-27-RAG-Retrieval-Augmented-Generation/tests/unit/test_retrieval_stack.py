from app.services.citation_service import CitationService
from app.services.chunking_service import ChunkingService
from app.services.document_loader_service import DocumentLoaderService
from app.services.embedding_service import EmbeddingService
from app.services.platform_status_service import PlatformStatusService
from app.services.retriever_service import RetrieverService
from app.services.statistics_service import StatisticsService
from app.services.vectorstore_service import VectorStoreService


def _build_index(test_settings):
    loader = DocumentLoaderService(test_settings)
    docs = loader.load_directory(test_settings.documents_dir)
    chunks = ChunkingService(test_settings).chunk_documents(docs)
    vectorstore = VectorStoreService(test_settings, EmbeddingService(test_settings))
    vectorstore.upsert_chunks(chunks)
    return vectorstore


def test_embedding_generation_returns_vector(test_settings):
    embedding_service = EmbeddingService(test_settings)
    vector = embedding_service.embed_query("alarm acknowledgment")
    assert isinstance(vector, list)
    assert len(vector) > 0


def test_vector_search_returns_results(test_settings):
    vectorstore = _build_index(test_settings)
    results = vectorstore.search("SNMP GET", top_k=3)
    assert len(results) > 0


def test_retriever_and_citations(test_settings):
    vectorstore = _build_index(test_settings)
    stats = StatisticsService()
    retriever = RetrieverService(test_settings, vectorstore, stats)
    rows, latency = retriever.retrieve("What is alarm SLA?", top_k=3)
    citations = CitationService().build_citations(rows)
    assert latency >= 0
    assert len(rows) > 0
    assert len(citations) > 0


def test_platform_status_includes_collection_metrics(test_settings):
    vectorstore = _build_index(test_settings)
    status_service = PlatformStatusService(test_settings, vectorstore)
    status = status_service.status()
    assert status["indexed_chunks"] > 0
