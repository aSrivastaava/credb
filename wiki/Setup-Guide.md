# Setup Guide

## Current State

The repository currently contains project documentation and architectural guidance. Exact setup commands will depend on the implementation structure once the backend, crawler, and frontend modules are added.

This page defines the intended setup path so the implementation can follow a clean deployment model.

## Planned Components

- `frontend/`
- `backend/`
- `crawler-agent/`
- `docs/`
- `wiki/`

## Prototype Setup Shape

Recommended for initial development:

- one machine
- React frontend
- Spring Boot backend
- local crawler
- SQLite or local PostgreSQL
- LM Studio running locally

## Multi-Machine Setup Shape

Recommended for lab deployment:

- central backend host
- PostgreSQL database
- one crawler agent per scanned machine
- frontend connected to central backend
- LM Studio reachable by backend over localhost or local network

## Suggested Environment Variables

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

## Setup Priorities

1. create backend module
2. create crawler module
3. define metadata schema
4. connect database
5. create frontend
6. integrate LM Studio
7. add semantic search

## Deployment Principles

- keep everything inside the trusted local network
- do not depend on cloud services
- let crawlers run near scanned disks
- keep AI optional

## To Update Later

Once implementation exists, extend this page with:

- exact prerequisites
- build commands
- local run commands
- environment setup examples
- screenshots or test flows
