from app.models.schemas import Citation


class CitationService:
    def build_citations(self, retrieved: list[dict]) -> list[Citation]:
        citations = []
        for item in retrieved:
            citations.append(
                Citation(
                    source=item.get("source", "unknown"),
                    chunk_id=item.get("chunk_id", "unknown"),
                    score=round(float(item.get("score", 0.0)), 4),
                    metadata=item.get("metadata", {}),
                )
            )
        return citations
