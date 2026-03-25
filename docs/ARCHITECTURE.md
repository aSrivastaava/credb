# Architecture

## Overview

CredB Nexus is designed as a modular offline-first search platform for distributed local environments. The architecture separates file crawling, indexing, retrieval, and AI-assisted reasoning so that each part can evolve independently without turning the system into a single slow pipeline.

## Recommended Logical Components

### 1. React Web UI

Responsibilities:

- Accept user queries
- Display indexed results
- Provide filtering and dashboard views
- Trigger crawl jobs and inspect status

The UI should remain thin. Business logic belongs in the backend.

### 2. Spring Boot Backend

Responsibilities:

- Expose REST APIs for search, crawl management, and health checks
- Coordinate crawler jobs
- Manage machine registration and crawl state
- Query the metadata database
- Call LM Studio only when semantic assistance is required

The backend can begin as one service, but it should maintain clear internal boundaries:

- Search service
- Crawl coordinator
- AI query service

### 3. Crawler Agents

Responsibilities:

- Traverse assigned file systems
- Extract metadata from files and directories
- Skip or flag inaccessible paths
- Batch records efficiently to the backend or database

These agents should run close to the machine being scanned. For single-machine prototypes, the crawler can initially run as part of the backend process, but multi-machine support is cleaner with separate crawler agents.

### 4. Metadata Database

Recommended role:

- Store deterministic search data and crawl state
- Support structured filtering and sorting
- Remain the system of record for indexed files

Recommended minimum fields:

- machine_id
- crawl_job_id
- file_name
- full_path
- extension
- size_bytes
- last_modified
- is_directory
- access_status
- indexed_at

Additional useful fields:

- file_hash
- owner
- content_type
- suspicious_flag

### 5. LLM Integration

LM Studio should be used as a local semantic processing layer, not as the primary search engine.

Use it for:

- Natural-language query interpretation
- Result summarization
- Query reformulation
- Semantic refinement on shortlisted results

Avoid using it for:

- Full-dataset scanning during crawling
- Per-file inference at index time for all records
- Replacing deterministic filtering logic

### 6. Optional Vector Store

A vector store is useful if semantic search becomes a major feature. It should remain optional until the basic indexing and search pipeline is stable.

Use it for:

- Embeddings over file names, metadata, and later selected content
- Similarity search
- Semantic ranking

Do not make it a prerequisite for the first working version.

## Recommended Request Flow

### Crawl Flow

1. Backend creates crawl job
2. Crawler agent receives scope
3. Agent scans directories in parallel
4. Metadata is batched and persisted
5. Crawl state is updated
6. Optional embedding generation runs after indexing

### Search Flow

1. User submits query
2. Backend parses structured filters and text intent
3. Metadata DB returns top candidates
4. Optional vector search expands or reranks candidates
5. LM Studio summarizes or refines only the top results
6. UI receives ranked results and metadata

## Production-Sensible Rules

- Keep crawling asynchronous
- Keep indexing deterministic
- Keep the relational database as the primary retrieval layer
- Call the LLM only on demand
- Batch writes to reduce database overhead
- Avoid hardcoding thread counts without profiling
- Treat hidden and system files as configurable policy, not unconditional exclusions

## Concurrency Guidance For Crawling

For Java crawler implementation:

- Use `ExecutorService` or a bounded thread pool
- Separate traversal, extraction, and write batching where practical
- Control parallelism based on machine resources
- Avoid excessive random I/O on slower disks

The goal is faster scanning without destabilizing host systems.

## Deployment Shapes

### Prototype

- One backend
- One local crawler
- SQLite
- React frontend
- Optional LM Studio

### Multi-Machine Lab Deployment

- Central backend
- PostgreSQL
- Multiple crawler agents
- React frontend
- Local LM Studio on the backend host
- Optional vector store

## Summary

The most important architectural decision is separation of concerns:

- crawl first
- index second
- retrieve third
- apply AI selectively

That gives CredB Nexus both performance and a clean path to scale.
