# Architecture

## Overview

CredB Nexus is designed as a modular platform with separate responsibilities for crawling, indexing, retrieval, and AI assistance. This prevents the system from becoming slow, tightly coupled, or difficult to scale.

## Main Components

### React Web UI

Responsibilities:

- accept search input
- display results
- show crawl status
- support filters and dashboard views

### Spring Boot Backend

Responsibilities:

- expose search and crawl APIs
- coordinate crawler jobs
- manage machine registration
- query the metadata database
- call LM Studio for semantic assistance

Logical backend boundaries:

- search service
- crawl coordinator
- AI query service

### Crawler Agents

Responsibilities:

- scan local file systems
- extract metadata
- report inaccessible paths
- submit batched results

For a real multi-machine deployment, crawler agents should run on the machines being scanned rather than forcing the central server to traverse everything remotely.

### Metadata Database

Purpose:

- store indexed file records
- support filtering and sorting
- keep crawl state
- act as the primary source of truth for retrieval

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

### LM Studio

Purpose:

- interpret natural-language queries
- refine shortlisted results
- summarize findings
- improve semantic relevance

Do not use LM Studio for full crawl-time inference over every file.

### Optional Vector Store

Purpose:

- store embeddings
- support similarity search
- improve semantic retrieval

This should remain optional until basic indexing and keyword search are stable.

## Recommended Request Flow

### Crawl

1. backend creates crawl job
2. crawler agent scans assigned roots
3. metadata is collected in batches
4. records are stored in the database
5. crawl state is updated

### Search

1. user submits query from UI
2. backend retrieves candidates from metadata DB
3. optional vector search reranks or expands candidates
4. LM Studio processes only the top results
5. UI receives ranked and explainable output

## Practical Guidance

- keep crawl work asynchronous
- keep indexing deterministic
- batch writes to the database
- do not send the full index to the LLM
- keep semantic search as an enhancement, not a dependency

## Prototype vs Production

### Prototype

- single backend
- local crawler
- SQLite or PostgreSQL
- local LM Studio

### Production-Style Lab Deployment

- central backend
- PostgreSQL
- distributed crawler agents
- LM Studio on the backend host
- optional vector store

## Summary

The most important architectural principle is simple:

- crawl first
- index second
- search third
- apply AI selectively
