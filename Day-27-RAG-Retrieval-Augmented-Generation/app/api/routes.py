from pathlib import Path

from fastapi import APIRouter, Depends, File, HTTPException, Query, Request, UploadFile

from app.models.envelope import ApiEnvelope, envelope
from app.models.markers import (
    ANSWER_GENERATED,
    DOCUMENT_INDEXED,
    DOCUMENT_RETRIEVED,
    EMBEDDINGS_CREATED,
    PLATFORM_READY,
    QUERY_COMPLETED,
    RAG_READY,
    SEARCH_COMPLETED,
    SOURCES_ATTACHED,
    VECTOR_DB_UPDATED,
)
from app.models.schemas import IndexRequest, QueryRequest
from app.services.container import ServiceContainer
from app.services.sample_documents_service import SampleDocumentsService

router = APIRouter()


def get_container(request: Request) -> ServiceContainer:
    return request.app.state.container


@router.post("/api/documents/upload", response_model=ApiEnvelope)
async def upload_document(
    request: Request,
    file: UploadFile = File(...),
    container: ServiceContainer = Depends(get_container),
):
    suffix = Path(file.filename or "").suffix.lower()
    allowed = {ext.strip().lower() for ext in container.settings.allowed_extensions.split(",") if ext.strip()}
    if suffix not in allowed:
        raise HTTPException(status_code=400, detail=f"Unsupported file extension: {suffix}")

    target_path = container.settings.documents_dir / (file.filename or "uploaded.txt")
    target_path.parent.mkdir(parents=True, exist_ok=True)
    content = await file.read()
    target_path.write_bytes(content)
    return envelope(True, DOCUMENT_INDEXED, "Document uploaded", {"file": str(target_path.name)})


@router.post("/api/documents/index", response_model=ApiEnvelope)
def index_documents(payload: IndexRequest, container: ServiceContainer = Depends(get_container)):
    if payload.rebuild:
        container.vectorstore_service.reset()

    SampleDocumentsService().ensure_seed_documents(container.settings.documents_dir)
    docs = container.document_loader_service.load_directory(container.settings.documents_dir)
    chunks = container.chunking_service.chunk_documents(docs)
    inserted = container.vectorstore_service.upsert_chunks(chunks)

    return envelope(
        True,
        VECTOR_DB_UPDATED,
        "Documents indexed",
        {
            "markers": [DOCUMENT_INDEXED, EMBEDDINGS_CREATED, VECTOR_DB_UPDATED],
            "document_count": len(docs),
            "chunk_count": len(chunks),
            "indexed_chunks": inserted,
        },
    )


@router.get("/api/documents", response_model=ApiEnvelope)
def list_documents(container: ServiceContainer = Depends(get_container)):
    docs = container.vectorstore_service.list_documents()
    return envelope(True, DOCUMENT_RETRIEVED, "Indexed documents", {"documents": docs, "count": len(docs)})


@router.post("/api/query", response_model=ApiEnvelope)
def query_rag(payload: QueryRequest, container: ServiceContainer = Depends(get_container)):
    result = container.rag_pipeline_service.answer(
        question=payload.question,
        conversation_id=payload.conversation_id,
        top_k=payload.top_k,
        metadata_filter=payload.metadata_filter,
    )
    return envelope(
        True,
        QUERY_COMPLETED,
        "Query completed",
        {
            "markers": [ANSWER_GENERATED, SOURCES_ATTACHED, QUERY_COMPLETED],
            **result.model_dump(),
        },
    )


@router.get("/api/search", response_model=ApiEnvelope)
def semantic_search(
    q: str = Query(..., min_length=2),
    top_k: int = Query(4, ge=1, le=20),
    container: ServiceContainer = Depends(get_container),
):
    rows, latency = container.retriever_service.retrieve(q, top_k=top_k)
    return envelope(
        True,
        SEARCH_COMPLETED,
        "Semantic search completed",
        {"markers": [DOCUMENT_RETRIEVED, SEARCH_COMPLETED], "results": rows, "latency_ms": round(latency, 3)},
    )


@router.get("/api/statistics", response_model=ApiEnvelope)
def statistics(container: ServiceContainer = Depends(get_container)):
    return envelope(True, RAG_READY, "Statistics snapshot", container.statistics_service.snapshot())


@router.delete("/api/vectorstore/reset", response_model=ApiEnvelope)
def reset_vectorstore(container: ServiceContainer = Depends(get_container)):
    container.vectorstore_service.reset()
    return envelope(True, VECTOR_DB_UPDATED, "Vectorstore reset", {"count": container.vectorstore_service.count()})


@router.get("/api/platform/status", response_model=ApiEnvelope)
def platform_status(container: ServiceContainer = Depends(get_container)):
    return envelope(True, PLATFORM_READY, "Platform ready", container.platform_status_service.status())


@router.get("/actuator/health", response_model=ApiEnvelope)
def health(container: ServiceContainer = Depends(get_container)):
    return envelope(True, RAG_READY, "Service healthy", {"status": "UP", **container.platform_status_service.status()})
