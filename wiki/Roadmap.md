# Roadmap

## Development Strategy

CredB Nexus should be built in phases so that each stage produces a usable, testable system.

## Phase 1: Foundation

- define repository structure
- define metadata schema
- define crawler scope
- finalize architecture

## Phase 2: Crawler MVP

- build recursive Java crawler
- support multi-threaded scanning
- extract core file metadata
- handle path and permission failures

## Phase 3: Indexing Layer

- persist metadata centrally
- support batch inserts
- store crawl jobs and machine identifiers
- validate retrieval performance

## Phase 4: Search API

- add Spring Boot search endpoints
- support filtering and pagination
- add crawl status endpoints
- add health endpoints

## Phase 5: Frontend

- build React search UI
- show results and filters
- display crawl status
- create dashboard views

## Phase 6: LM Studio Integration

- integrate semantic query flow
- summarize shortlisted results
- support natural-language requests
- avoid LLM use during crawling

## Phase 7: Semantic Search

- generate embeddings for selected records
- store vectors locally
- support similarity search or reranking
- compare results with keyword search

## Phase 8: Hardening

- improve performance
- improve retry and failure handling
- validate multi-machine deployments
- add monitoring and observability

## Milestones

- `M1`: crawler works on one machine
- `M2`: metadata stored in database
- `M3`: search API available
- `M4`: frontend connected
- `M5`: LM Studio integrated
- `M6`: multi-machine support added
- `M7`: semantic search added

## Priority Rule

Always build in this order:

1. crawling
2. indexing
3. retrieval
4. UI
5. AI refinement

That sequence keeps the project practical and testable.
