# Architecture

CredB Nexus is planned as an offline-first distributed file indexing and retrieval platform for local networks.

## Core Components

- `Crawler Agents`
  - scan local file systems
  - extract metadata
  - send indexed records to the backend
  - implemented in Python (3.11+)
- `Backend`
  - manages crawl jobs
  - stores metadata
  - exposes search APIs
  - integrates with Cloudflare AI when semantic search is needed
- `Frontend`
  - provides search and dashboard views
- `Metadata Database`
  - stores indexed file records and crawl state
- `Optional LLM Layer`
  - uses Cloudflare Workers AI free-tier models
  - refines or summarizes shortlisted results

## Stack

| Component       | Technology                        |
|-----------------|-----------------------------------|
| Crawler agent   | Python 3.11+, stdlib only         |
| Backend         | Spring Boot (Java 17)             |
| Frontend        | React                             |
| Semantic search | Cloudflare Workers AI (free tier) |
| Vector search   | Planned (Phase 7)                 |

## Architectural Rules

- crawling must stay separate from LLM execution
- metadata search should work without semantic search
- semantic search should enhance retrieval, not replace indexing
- the system should remain usable in offline environments
- Cloudflare AI calls are always optional and non-blocking
