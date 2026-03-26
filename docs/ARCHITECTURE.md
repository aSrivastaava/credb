# Architecture

CredB Nexus is planned as an offline-first distributed file indexing and retrieval platform for local networks.

## Core Components

- `Crawler Agents`
  - scan local file systems
  - extract metadata
  - send indexed records to the backend
- `Backend`
  - manages crawl jobs
  - stores metadata
  - exposes search APIs
  - integrates with LM Studio when semantic search is needed
- `Frontend`
  - provides search and dashboard views
- `Metadata Database`
  - stores indexed file records and crawl state
- `Optional LLM Layer`
  - refines or summarizes shortlisted results

## Storage Evolution

Near term:

- crawler output may be written to temporary local JSON files for debugging or handoff

Planned later stage:

- central PostgreSQL hosted on the local Ubuntu server
- pgvector enabled for semantic retrieval and RAG-oriented workflows
- crawler agents send structured metadata into the database instead of relying on file export as the long-term contract

## Architectural Rules

- crawling must stay separate from LLM execution
- metadata search should work without semantic search
- semantic search should enhance retrieval, not replace indexing
- the system should remain usable in offline environments
