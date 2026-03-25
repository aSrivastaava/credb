# CredB Nexus

CredB Nexus is an offline-first distributed file indexing and intelligent retrieval platform for local networks.

This repository has been reset to a clean starting point and will be rebuilt in phases.

## Planned Direction

- distributed crawler agents
- centralized metadata indexing
- Spring Boot backend
- React frontend
- optional LM Studio integration for semantic search

## Status

Fresh repository baseline on `main`.

## Documentation

- `docs/ARCHITECTURE.md`
- `docs/ROADMAP.md`
- `docs/DEPLOYMENT.md`
- `docs/API_OVERVIEW.md`
- `docs/REPOSITORY_RULES.md`
- `docs/SETUP_GUIDE.md`

## Repository Structure

```text
./
|- backend/
|- crawler-agent/
|- frontend/
|- docs/
|- scripts/
`- .github/
```

## Current Scaffold

- `backend/` for the Spring Boot service
- `crawler-agent/` for filesystem crawling
- `frontend/` for the web UI
