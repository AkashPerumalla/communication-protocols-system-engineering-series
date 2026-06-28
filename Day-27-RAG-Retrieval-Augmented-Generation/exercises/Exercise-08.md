# Exercise-08

## Objective
Apply enterprise RAG concepts in an operations-focused scenario.

## Architecture
Use FastAPI + ChromaDB + Sentence Transformers + citation-enabled response flow.

## Steps
1. Start API and index documents.
2. Execute targeted queries for NOC and telecom workflows.
3. Validate retrieved sources and latency metrics.

## Expected Output
Marker-based response with grounded answer and citations.

## Solution
Use /api/documents/index, /api/search, and /api/query with top_k tuning and optional metadata filters.

## Learning Outcome
Understand how retrieval quality, chunking, and citations affect production RAG reliability.
