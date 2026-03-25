# Phase 1 Foundation

## Objective

Phase 1 establishes the repository, architecture boundaries, and implementation starting points for CredB Nexus.

## Deliverables

- repository structure
- module placeholders
- environment configuration baseline
- metadata schema draft
- implementation conventions

## Repository Layout

```text
credb/
+- backend/
+- crawler-agent/
+- frontend/
+- docs/
+- .github/
+- .env.example
+- .gitignore
+- README.md
```

## Phase 1 Decisions

### System Shape

- one central backend
- one or more crawler agents
- one web frontend
- one relational database
- optional local LLM integration

### Primary Rules

- keep crawl and AI concerns separated
- keep deterministic indexing as the base system
- keep semantic search optional
- keep deployment local-network friendly

## Outputs Of This Phase

- scaffolded module folders
- backend, crawler, and frontend starter docs
- schema draft for indexed file metadata
- configuration template for future implementation

## Exit Criteria

Phase 1 is complete when:

- project structure exists
- module responsibilities are defined
- schema draft exists
- environment variables are documented
- Phase 2 crawler work can begin without structural ambiguity
