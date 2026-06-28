# Day-27 - RAG Retrieval Augmented Generation Interview Questions

## Beginner
1. What is Retrieval-Augmented Generation (RAG)?
Answer: RAG combines retrieval from a knowledge base with LLM generation to produce grounded answers.
2. Why is RAG preferred over pure prompting for enterprise documentation?
Answer: It reduces hallucination by grounding responses in retrieved source text.
3. What is the role of embeddings in RAG?
Answer: Embeddings convert text to vectors for semantic similarity search.
4. What is ChromaDB used for?
Answer: Persistent vector storage and nearest-neighbor retrieval.
5. Why do we split documents into chunks?
Answer: To improve retrieval granularity and context fit for LLM prompts.
6. What is top-k retrieval?
Answer: Returning the k most semantically similar chunks for a query.
7. What is citation attachment in RAG?
Answer: Mapping answer evidence to source documents and chunk identifiers.
8. Why is metadata useful in vector search?
Answer: It allows filter-based retrieval, such as domain-specific or environment-specific search.
9. What does chunk overlap control?
Answer: Context continuity between adjacent chunks.
10. Why do we need an API response envelope?
Answer: It standardizes client handling and operational observability.
11. What does /actuator/health provide?
Answer: Runtime health status for monitoring and orchestration systems.
12. Why use deterministic sample documents in learning projects?
Answer: They make tests reproducible and validation consistent.
13. What is semantic similarity search?
Answer: Retrieval based on vector meaning rather than exact keywords.
14. Why use FastAPI for this project?
Answer: It provides high-performance async APIs with validation and docs.
15. Why include Streamlit here?
Answer: It enables quick operator-facing workflows for upload and querying.

## Intermediate
16. How does chunk size impact retrieval quality?
Answer: Larger chunks keep context but may dilute relevance; smaller chunks improve precision but can lose continuity.
17. How does chunk overlap impact performance?
Answer: Higher overlap can improve context recall but increases storage and retrieval redundancy.
18. What retrieval metric does Chroma commonly return?
Answer: Distance values that can be transformed into relative similarity scores.
19. How can metadata filtering improve precision?
Answer: It restricts candidate chunks to relevant domains like SNMP or SatCom.
20. What failure mode occurs with stale vector stores?
Answer: Answers may cite outdated content if indexing is not refreshed.
21. Why should citation IDs include chunk indexes?
Answer: They make evidence traceable and auditable.
22. How do you make LLM integration pluggable?
Answer: Use provider abstraction and configuration-driven model selection.
23. Why use a deterministic mock LLM in tests?
Answer: To avoid network flakiness, cost, and nondeterministic output.
24. What are typical ingestion errors for PDF and DOCX?
Answer: Parsing failures, encrypted files, malformed formatting, and empty extraction text.
25. How do you handle unsupported file uploads safely?
Answer: Validate extensions and reject with clear 4xx errors.
26. What is prompt augmentation?
Answer: Building the LLM prompt using user question plus retrieved context.
27. What should happen if retrieval returns no context?
Answer: Return a grounded fallback stating evidence is insufficient.
28. How do you measure retrieval latency?
Answer: Time query-to-results inside retriever service and publish statistics.
29. Why persist ChromaDB to disk?
Answer: To survive restarts and avoid re-indexing on every startup.
30. How do you prevent response schema drift?
Answer: Use strict Pydantic models and integration tests for envelopes.
31. Why centralize settings in Pydantic?
Answer: It ensures environment consistency and clear runtime configuration.
32. How do you support multi-turn Q&A?
Answer: Track conversation history keyed by conversation ID.
33. What is the role of health checks in Dockerized RAG services?
Answer: They ensure orchestration only routes traffic to healthy instances.
34. Why keep marker strings stable?
Answer: Automation and observability rely on exact marker matching.
35. How would you tune top-k?
Answer: Run relevance/latency experiments over representative query sets.

## Advanced
36. How do you design a retrieval evaluation benchmark?
Answer: Define labeled queries, expected source chunks, precision@k, and latency thresholds.
37. What risks arise from mixing operational domains in one collection?
Answer: Cross-domain noise can degrade precision without metadata filters or reranking.
38. How can you improve citation correctness beyond top-k retrieval?
Answer: Add reranking and answer-to-source attribution checks.
39. What does idempotent indexing mean in RAG systems?
Answer: Re-running indexing should not duplicate chunk records or corrupt metadata.
40. How do you version enterprise knowledge corpora?
Answer: Track document revision metadata and collection version tags.
41. What is a retrieval drift signal?
Answer: Falling relevance metrics as documents, embeddings, or query patterns evolve.
42. How do you secure enterprise RAG APIs?
Answer: Add authN/authZ, key management, PII scrubbing, and audit logging.
43. Why may vector distance-to-score mapping be misleading?
Answer: Different embedding spaces require calibrated interpretation, not raw inversion assumptions.
44. How do you scale Chroma-backed retrieval?
Answer: Partition collections, optimize hardware, and cache frequent query vectors.
45. What is the trade-off between online and offline indexing?
Answer: Online indexing enables freshness; offline indexing improves control and performance predictability.
46. How would you detect hallucinations in production?
Answer: Compare generated claims against retrieved evidence and flag unsupported assertions.
47. What MLOps artifacts should this project produce?
Answer: Retrieval metrics, latency trends, corpus versions, and experiment configs.
48. How do you enforce strict grounding in prompts?
Answer: Require evidence-bound answers and explicit unknown handling when context is insufficient.
49. How do metadata filters help NOC workflows?
Answer: Operators can query only alarm, satcom, or SNMP domains during incidents.
50. What architecture changes are needed for multi-tenant enterprise RAG?
Answer: Tenant-isolated collections, scoped auth, per-tenant config, and quota-aware indexing.
