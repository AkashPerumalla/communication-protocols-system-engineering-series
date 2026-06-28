# Day-27 RAG Retrieval Augmented Generation

Enterprise AI Knowledge Assistant for NOC, Telecom, SatCom, and IoT operations. This module implements a production-style Retrieval-Augmented Generation pipeline that answers engineering questions from technical documentation with source citations.

## Learning Objectives
- Build a complete FastAPI-based RAG backend for enterprise operations knowledge.
- Ingest mixed-format documents: PDF, DOCX, TXT, Markdown.
- Generate embeddings with Sentence Transformers and persist vectors in ChromaDB.
- Perform semantic retrieval with top-k and metadata filtering.
- Attach source citations to all generated answers.
- Track retrieval and latency statistics.
- Expose operational endpoints with deterministic marker-based responses.
- Validate quality using unit tests, integration tests, and smoke automation.

## Tech Stack
- Python 3.11
- FastAPI
- LangChain Text Splitters
- ChromaDB
- Sentence Transformers (all-MiniLM-L6-v2)
- OpenAI API (pluggable)
- PyPDF
- python-docx
- Unstructured
- Pydantic
- Uvicorn
- Streamlit
- Docker

## Project Structure
- app/api: FastAPI route handlers
- app/config: Environment and runtime settings
- app/services: Core service layer and pipeline orchestration
- app/prompts: Prompt templates
- data/documents: Deterministic sample knowledge base
- data/chromadb: Persistent vector storage
- streamlit: Frontend operator console
- scripts: Automation and smoke validation
- tests: Unit and integration tests
- diagrams: Mermaid architecture diagrams
- exercises: Practical hands-on exercises

## RAG Pipeline
Document Loader -> Text Splitter -> Embedding Generator -> ChromaDB -> Retriever -> Prompt Augmentation -> LLM -> Answer + Citations

## REST APIs
- POST /api/documents/upload
- POST /api/documents/index
- GET /api/documents
- POST /api/query
- GET /api/search
- GET /api/statistics
- DELETE /api/vectorstore/reset
- GET /api/platform/status
- GET /actuator/health

## Response Envelope
All endpoints return:
{
  "success": true,
  "marker": "RAG_READY",
  "message": "",
  "data": {},
  "timestamp": ""
}

## Operational Markers
- RAG_READY
- DOCUMENT_INDEXED
- EMBEDDINGS_CREATED
- VECTOR_DB_UPDATED
- DOCUMENT_RETRIEVED
- ANSWER_GENERATED
- SOURCES_ATTACHED
- QUERY_COMPLETED
- SEARCH_COMPLETED
- PLATFORM_READY

## Deterministic Knowledge Base
Seed corpus includes the following domains:
- Device Manuals
- Alarm Procedures
- Network Troubleshooting Guides
- SNMP Documentation
- REST API Documentation
- Kubernetes Notes
- Docker Guide
- Incident Response Runbooks
- Telecom SOPs
- SatCom Operations Guide

## Run Locally
1. Install dependencies:
   pip install -r requirements.txt
2. Start API:
   uvicorn app.main:app --host 0.0.0.0 --port 8092
3. Index documents:
   curl -X POST http://localhost:8092/api/documents/index -H 'Content-Type: application/json' -d '{"rebuild": true}'
4. Launch Streamlit:
   streamlit run streamlit/app.py --server.port 8501

## Run Automation
./scripts/run_all_tests.sh

## Docker
- Build:
  docker build -t day27-rag:latest .
- Run:
  docker run --rm -p 8092:8092 -v $(pwd)/data/chromadb:/app/data/chromadb day27-rag:latest

## Evaluation Guidance
- Retrieval relevance: Inspect top-k context overlap with expected source docs.
- Chunk quality: Tune chunk_size/chunk_overlap and compare hit quality.
- Citation correctness: Verify source/chunk metadata references align with retrieved context.
- Latency metrics: Track /api/statistics for retrieval timing trends.
- Metadata filtering: Compare filtered vs unfiltered precision.

## Quality Gates Implemented
- Layered enterprise architecture with service decomposition.
- Constructor dependency injection through service container.
- Centralized Pydantic configuration.
- Deterministic sample data generation.
- Structured marker-based API envelope.
- API + service tests and smoke automation.
- Containerized deployment with healthcheck and persistent vector volume.

## Diagrams
- diagrams/01-rag-pipeline.mmd
- diagrams/02-document-processing-flow.mmd
- diagrams/03-embedding-workflow.mmd
- diagrams/04-retrieval-architecture.mmd
- diagrams/05-query-lifecycle.mmd
- diagrams/06-vector-db-architecture.mmd
- diagrams/07-enterprise-knowledge-assistant.mmd

## Exercises
Eight practical exercises are available under exercises/ with objective, architecture, steps, expected output, solution, and learning outcome.
