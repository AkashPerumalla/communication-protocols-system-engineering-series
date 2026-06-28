from langchain_text_splitters import RecursiveCharacterTextSplitter

from app.config.settings import AppSettings


class ChunkingService:
    def __init__(self, settings: AppSettings) -> None:
        self.settings = settings
        self.splitter = RecursiveCharacterTextSplitter(
            chunk_size=self.settings.chunk_size,
            chunk_overlap=self.settings.chunk_overlap,
            separators=["\n\n", "\n", ". ", " ", ""],
        )

    def chunk_documents(self, documents: list[dict]) -> list[dict]:
        chunks: list[dict] = []
        for doc in documents:
            parts = self.splitter.split_text(doc["content"])
            for index, part in enumerate(parts):
                chunks.append(
                    {
                        "chunk_id": f"{doc['document_id']}::{index}",
                        "text": part,
                        "source": doc["source"],
                        "document_id": doc["document_id"],
                        "metadata": {**doc["metadata"], "chunk_index": index},
                    }
                )
        return chunks
