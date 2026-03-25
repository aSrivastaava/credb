# Deployment Notes

## Objective

Deploy CredB Nexus in a way that preserves its offline-first and local-network design goals.

## Supported Deployment Shapes

### 1. Prototype Deployment

Use this for:

- local demos
- academic submissions
- single-machine validation

Suggested shape:

- React frontend on one machine
- Spring Boot backend on the same machine
- local crawler process
- SQLite or local PostgreSQL
- LM Studio running locally

### 2. Lab Or Department Deployment

Use this for:

- multiple systems in one local network
- institutional file discovery
- centralized inspection workflows

Suggested shape:

- React frontend on a central host
- Spring Boot backend on a central host
- PostgreSQL on the same host or a dedicated internal server
- one crawler agent per scanned machine
- LM Studio on the backend host
- optional vector store

## Deployment Principles

- Keep all components within the trusted local network
- Prefer internal IP communication
- Do not require cloud APIs
- Keep crawler agents close to the file systems they scan
- Centralize only metadata and query logic

## Recommended Environment Variables

These names are suggestions for future implementation:

```env
CREDB_DB_URL=
CREDB_DB_USER=
CREDB_DB_PASSWORD=
CREDB_BACKEND_PORT=
CREDB_FRONTEND_PORT=
CREDB_LMSTUDIO_BASE_URL=
CREDB_CRAWLER_MACHINE_ID=
CREDB_CRAWLER_SCAN_ROOTS=
CREDB_VECTOR_ENABLED=
```

## Database Choice

### SQLite

Best for:

- early development
- local prototype
- single-machine demo

### PostgreSQL

Best for:

- multiple crawler agents
- larger indexes
- concurrent search requests
- production-style architecture

## LM Studio Placement

Recommended:

- run LM Studio where the backend can reach it with low latency
- keep inference local to the network
- only invoke the model for semantic tasks

Avoid:

- pushing all indexed data through the LLM
- using the LLM during raw crawling

## Security Considerations

- limit crawler access to approved scan roots
- authenticate backend API access if multiple users are supported
- treat file paths and metadata as sensitive operational data
- log crawl failures without leaking unnecessary path details
- sanitize all user search input before passing it to search or AI layers

## Scaling Guidance

To scale safely:

- add crawler agents before splitting the backend
- optimize batch insertion before adding semantic search
- profile relational search before introducing a vector database
- keep semantic retrieval optional

## Pre-Deployment Checklist

- define scan roots
- define allowed machines
- choose database
- verify crawl permissions
- verify LM Studio availability
- test with representative file trees
- document expected host requirements

## Post-Deployment Validation

- confirm crawler registration
- run a small crawl job
- verify metadata persistence
- test keyword search
- test optional semantic query flow
- inspect failure logging

## Summary

The cleanest deployment path is:

- prototype locally first
- move to centralized backend plus distributed crawler agents next
- add semantic retrieval only after deterministic search is stable
