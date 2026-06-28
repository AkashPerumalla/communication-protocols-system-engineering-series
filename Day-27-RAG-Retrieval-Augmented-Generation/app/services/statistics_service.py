from dataclasses import dataclass


@dataclass
class StatisticsState:
    retrieval_count: int = 0
    total_retrieval_latency_ms: float = 0.0
    total_retrieved_chunks: int = 0
    query_count: int = 0


class StatisticsService:
    def __init__(self) -> None:
        self.state = StatisticsState()

    def record_retrieval(self, latency_ms: float, chunk_count: int) -> None:
        self.state.retrieval_count += 1
        self.state.total_retrieval_latency_ms += latency_ms
        self.state.total_retrieved_chunks += chunk_count

    def record_query(self) -> None:
        self.state.query_count += 1

    def snapshot(self) -> dict:
        avg_latency = 0.0
        if self.state.retrieval_count > 0:
            avg_latency = self.state.total_retrieval_latency_ms / self.state.retrieval_count
        return {
            "query_count": self.state.query_count,
            "retrieval_count": self.state.retrieval_count,
            "avg_retrieval_latency_ms": round(avg_latency, 3),
            "total_retrieved_chunks": self.state.total_retrieved_chunks,
        }
