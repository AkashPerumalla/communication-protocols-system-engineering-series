from pathlib import Path
from typing import Any

import chromadb

from app.config.settings import AppSettings
from app.services.embedding_service import EmbeddingService


class VectorStoreService:
    def __init__(self, settings: AppSettings, embedding_service: EmbeddingService) -> None:
        self.settings = settings
        self.embedding_service = embedding_service
        Path(self.settings.chromadb_dir).mkdir(parents=True, exist_ok=True)
        self.client = chromadb.PersistentClient(path=str(self.settings.chromadb_dir))
        self.collection = self.client.get_or_create_collection(name=self.settings.collection_name)

    def upsert_chunks(self, chunks: list[dict]) -> int:
        if not chunks:
            return 0
        ids = [chunk["chunk_id"] for chunk in chunks]
        texts = [chunk["text"] for chunk in chunks]
        embeddings = self.embedding_service.embed_texts(texts)
        metadatas = [{**chunk["metadata"], "source": chunk["source"], "document_id": chunk["document_id"]} for chunk in chunks]
        self.collection.upsert(ids=ids, documents=texts, embeddings=embeddings, metadatas=metadatas)
        return len(chunks)

    def search(self, query: str, top_k: int, metadata_filter: dict[str, Any] | None = None) -> list[dict]:
        embedding = self.embedding_service.embed_query(query)
        results = self.collection.query(
            query_embeddings=[embedding],
            n_results=top_k,
            where=metadata_filter,
        )

        documents = results.get("documents", [[]])[0]
        distances = results.get("distances", [[]])[0]
        metadatas = results.get("metadatas", [[]])[0]
        ids = results.get("ids", [[]])[0]

        output = []
        for idx, doc in enumerate(documents):
            output.append(
                {
                    "chunk_id": ids[idx],
                    "text": doc,
                    "score": float(1.0 - distances[idx]) if idx < len(distances) else 0.0,
                    "metadata": metadatas[idx] if idx < len(metadatas) else {},
                    "source": (metadatas[idx] or {}).get("source", "unknown") if idx < len(metadatas) else "unknown",
                }
            )
        return output

    def list_documents(self) -> list[str]:
        rows = self.collection.get(include=["metadatas"])
        names = {(item or {}).get("source", "unknown") for item in rows.get("metadatas", [])}
        return sorted(names)

    def count(self) -> int:
        return int(self.collection.count())

    def reset(self) -> None:
        self.client.delete_collection(self.settings.collection_name)
        self.collection = self.client.get_or_create_collection(name=self.settings.collection_name)
