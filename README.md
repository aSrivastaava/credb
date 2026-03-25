# CredB Nexus

CredB Nexus is an offline-first distributed file indexing and intelligent retrieval platform built for local networks. It combines fast deterministic crawling with AI-assisted search so administrators and users can discover files, inspect systems, and query indexed data across multiple machines without depending on cloud services.

The project started from a practical lab problem: manually checking each machine for unauthorized, suspicious, or misplaced files is slow, repetitive, and unreliable. CredB Nexus turns that manual process into an automated pipeline using a Java-based crawler, a centralized indexing backend, a React web interface, and a locally hosted LLM through LM Studio.

## Why CredB Nexus

- Manual file inspection does not scale across multiple systems
- Standard operating system search is isolated to one machine at a time
- Keyword-only search misses context and intent
- Sensitive environments often require offline or local-only processing

CredB Nexus addresses those constraints with distributed crawling, centralized metadata indexing, and optional semantic retrieval.

## Core Capabilities

- Crawl directories across one or more machines on a local network
- Build a centralized searchable index of files and folders
- Expose search and dashboard functionality through a web UI
- Support fast metadata-based search with filters and ranking
- Add semantic search and contextual responses through a local LLM
- Operate in offline or privacy-sensitive environments

## High-Level Architecture

CredB Nexus is best structured as a modular system with clear operational boundaries:

- `React Web UI`
  - Search interface
  - Filters, dashboards, and result exploration
- `Spring Boot Backend`
  - Search API
  - Crawl orchestration
  - AI query handling
- `Crawler Agents`
  - File system traversal
  - Metadata extraction
  - Batch submission of indexed records
- `Metadata Database`
  - File paths
  - Names
  - extensions
  - timestamps
  - machine identity
  - crawl state
- `LLM Integration`
  - Local inference through LM Studio
  - Query understanding
  - Summarization and semantic refinement
- `Optional Vector Store`
  - Embeddings for semantic retrieval
  - Similarity search over selected metadata or content

## Recommended Data Flow

1. A crawl job is created for one or more machines.
2. A crawler agent scans assigned directories in the background.
3. File metadata is normalized and stored in the central metadata database.
4. The user submits a search query from the web UI.
5. The backend retrieves matches using relational filters, keyword search, or both.
6. If semantic assistance is enabled, only the top candidate results are sent to the local LLM.
7. The backend returns ranked results, explanations, or summaries to the UI.

This separation is important: crawling and indexing stay fast and deterministic, while LLM usage remains on-demand.

## Technology Direction

- `Frontend`: React
- `Backend`: Java Spring Boot
- `Crawler`: Java, multi-threaded
- `Primary database`: PostgreSQL for multi-machine deployments, SQLite for local prototypes
- `AI runtime`: LM Studio
- `Vector search`: optional FAISS or another local vector store

## Design Principles

- Offline-first by default
- Separate crawling from AI processing
- Use relational search as the primary retrieval layer
- Use LLMs only for refinement, explanation, or semantic ranking
- Keep deployment modular so the system can start small and scale later

## Practical Use Cases

- Lab-wide inspection for unauthorized files
- Administrative file audits across multiple systems
- Search and retrieval in local institutional networks
- Semantic lookup of suspicious or policy-relevant files
- Local knowledge discovery where cloud services are restricted

## Current Documentation

- [Architecture](./docs/ARCHITECTURE.md)
- [Implementation Roadmap](./docs/ROADMAP.md)
- [Deployment Notes](./docs/DEPLOYMENT.md)
- [API Overview](./docs/API_OVERVIEW.md)

## Suggested Repository Structure

The codebase is not scaffolded yet, but this is a clean starting layout:

```text
credb-nexus/
├─ backend/
├─ crawler-agent/
├─ frontend/
├─ docs/
│  ├─ ARCHITECTURE.md
│  ├─ ROADMAP.md
│  ├─ DEPLOYMENT.md
│  └─ API_OVERVIEW.md
└─ README.md
```

## What Makes This Architecture Sensible

- Crawling runs in the background and should not depend on the LLM
- Indexing is fast, predictable, and easier to debug when separated from inference
- Search can remain useful even if semantic features are disabled
- LM Studio improves query understanding without becoming the system bottleneck
- The system can begin as a single deployment and evolve into a distributed one

## Project Status

This repository currently contains the project documentation baseline. The implementation can be added incrementally using the architecture and roadmap in `docs/`.

## Near-Term Build Plan

1. Create the Java crawler and metadata model
2. Build the Spring Boot backend and search endpoints
3. Add PostgreSQL or SQLite integration
4. Build the React interface
5. Add LM Studio query refinement
6. Add optional embeddings and semantic ranking

## Future Enhancements

- Incremental crawling
- Real-time file change monitoring
- Role-based access control
- Suspicious file detection rules
- Duplicate file detection
- Content extraction for supported text documents
- Crawl scheduling and machine health monitoring
- Analytics dashboards

## Notes

- Do not run the LLM during raw crawling
- Do not send every indexed file to the LLM
- Use the database for recall and filtering
- Use the LLM only on shortlisted results or semantic workflows

## License

Add a project license before public release.
