from app.services.chunking_service import ChunkingService
from app.services.document_loader_service import DocumentLoaderService


def test_document_loading_reads_seed_documents(test_settings):
    loader = DocumentLoaderService(test_settings)
    docs = loader.load_directory(test_settings.documents_dir)
    assert len(docs) >= 10
    assert any(doc["source"].endswith(".pdf") for doc in docs)
    assert any(doc["source"].endswith(".docx") for doc in docs)


def test_chunking_creates_overlap_chunks(test_settings):
    loader = DocumentLoaderService(test_settings)
    docs = loader.load_directory(test_settings.documents_dir)
    chunking = ChunkingService(test_settings)
    chunks = chunking.chunk_documents(docs)
    assert len(chunks) > 0
    assert all("chunk_id" in chunk for chunk in chunks)
