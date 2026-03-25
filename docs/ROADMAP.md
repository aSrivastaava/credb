# Implementation Roadmap

## Goal

Build CredB Nexus incrementally so that each stage produces a usable system before the next layer is added.

## Phase 1: Foundation

Objectives:

- Finalize core requirements
- Define metadata schema
- Decide prototype deployment shape
- Establish repository structure

Deliverables:

- Backend module skeleton
- Frontend module skeleton
- Crawler module skeleton
- Initial database schema draft
- Core documentation baseline

## Phase 2: Crawler MVP

Objectives:

- Implement recursive directory traversal in Java
- Extract basic metadata
- Handle access-denied and invalid-path scenarios
- Support controlled parallel crawling

Deliverables:

- Multi-threaded crawler
- Metadata model
- Crawl status reporting
- Sample crawl output

## Phase 3: Central Indexing

Objectives:

- Persist file metadata centrally
- Support batching for large writes
- Store crawl jobs and machine identity
- Prepare indexed records for retrieval

Deliverables:

- Database schema
- Persistence layer
- Crawl job table
- Basic indexing pipeline

## Phase 4: Search API

Objectives:

- Build search endpoints in Spring Boot
- Support path, name, extension, size, and date filters
- Return paginated results
- Add health and crawl status endpoints

Deliverables:

- Search API
- Crawl management API
- Error handling and validation
- Initial API documentation

## Phase 5: Frontend

Objectives:

- Build a React search interface
- Add result table or card views
- Add filter controls
- Display crawl jobs and machine coverage

Deliverables:

- Search page
- Dashboard page
- Crawl status view
- Basic responsive UI

## Phase 6: AI Integration

Objectives:

- Connect backend to LM Studio
- Use natural-language queries safely
- Summarize or refine top search results
- Avoid using LLMs as the raw index engine

Deliverables:

- LM Studio service integration
- Prompt strategy for result summarization
- Semantic query endpoint
- Search plus AI response flow

## Phase 7: Semantic Search

Objectives:

- Add embeddings for selected metadata or content
- Store vectors locally
- Support semantic retrieval or reranking
- Compare quality against keyword-only search

Deliverables:

- Embedding pipeline
- Vector search integration
- Evaluation dataset
- Relevance comparison notes

## Phase 8: Hardening

Objectives:

- Improve performance on large file trees
- Reduce crawl duplication
- Add logging and observability
- Validate multi-machine behavior

Deliverables:

- Performance benchmarks
- Retry and failure handling
- Structured logs
- Deployment test results

## Suggested Milestones

- `M1`: Single-machine crawler with metadata output
- `M2`: Database-backed indexing
- `M3`: Search API and query filters
- `M4`: React search UI
- `M5`: LM Studio integration
- `M6`: Multi-machine crawl support
- `M7`: Optional semantic vector search

## Priority Order

Build in this order:

1. crawler
2. indexing
3. search API
4. frontend
5. LLM refinement
6. semantic search

This order keeps the project usable at every stage.

## Risks To Manage

- Crawl performance on very large drives
- Access permission failures
- Overuse of the LLM in latency-sensitive paths
- Schema churn during early implementation
- Multi-machine coordination complexity

## Success Criteria

CredB Nexus is successful when it can:

- crawl real directory trees reliably
- store index data centrally
- return useful filtered search results quickly
- support multi-machine workflows
- add AI assistance without blocking normal search
