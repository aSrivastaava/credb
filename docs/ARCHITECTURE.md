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

## Architectural Rules

- crawling must stay separate from LLM execution
- metadata search should work without semantic search
- semantic search should enhance retrieval, not replace indexing
- the system should remain usable in offline environments
