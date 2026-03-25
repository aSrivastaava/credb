# CredB Nexus Wiki

Welcome to the CredB Nexus wiki.

CredB Nexus is an offline-first distributed file indexing and intelligent retrieval platform for local networks. It combines deterministic metadata search with selective AI assistance to support fast file discovery across multiple machines.

## What CredB Nexus Does

- crawls directories across local machines
- builds a centralized file metadata index
- provides a web interface for search and monitoring
- supports AI-assisted semantic search through LM Studio
- works in offline or privacy-sensitive environments

## Wiki Contents

- [Architecture](Architecture.md)
- [Setup Guide](Setup-Guide.md)
- [API Reference](API-Reference.md)
- [Roadmap](Roadmap.md)
- [FAQ](FAQ.md)

## Recommended System Shape

- `React Web UI`
- `Spring Boot Backend`
- `Crawler Agents`
- `Metadata Database`
- `Optional Vector Store`
- `LM Studio`

## Key Design Rules

- keep crawling separate from AI inference
- use relational search as the primary retrieval layer
- invoke the LLM only on shortlisted results
- support distributed crawling through machine-local agents

## Typical Flow

1. create a crawl job
2. scan files through crawler agents
3. store normalized metadata centrally
4. query indexed records from the web UI
5. optionally refine top results with LM Studio

## Notes

This wiki is designed to stay aligned with the repository docs under `docs/`. As implementation is added, these pages should be updated with exact module names, endpoints, and run instructions.
