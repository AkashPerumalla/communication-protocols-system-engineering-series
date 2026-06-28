import hashlib

from app.config.settings import AppSettings


class EmbeddingService:
    def __init__(self, settings: AppSettings) -> None:
        self.settings = settings
        self._model = None

    def embed_texts(self, texts: list[str]) -> list[list[float]]:
        if self.settings.use_mock_embeddings:
            return [self._mock_vector(text) for text in texts]
        if self._model is None:
            try:
                from sentence_transformers import SentenceTransformer
            except ImportError:
                return [self._mock_vector(text) for text in texts]
            self._model = SentenceTransformer(self.settings.embedding_model_name)
        vectors = self._model.encode(texts, normalize_embeddings=True)
        return [vector.tolist() for vector in vectors]

    def embed_query(self, query: str) -> list[float]:
        return self.embed_texts([query])[0]

    def _mock_vector(self, text: str, size: int = 16) -> list[float]:
        digest = hashlib.sha256(text.encode("utf-8")).digest()
        values = []
        for i in range(size):
            values.append((digest[i] / 255.0) - 0.5)
        return values
