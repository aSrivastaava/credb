# Setup Guide

This repository is currently in baseline documentation stage. Exact build and run commands will be added once the backend, crawler, and frontend modules are scaffolded.

## Planned Components

- `backend/`
- `crawler-agent/`
- `frontend/`
- `docs/`

## Configuration Baseline

Use `.env.example` as the initial reference for future environment configuration.

Current planned variables:

- `CREDB_DB_URL`
- `CREDB_DB_USER`
- `CREDB_DB_PASSWORD`
- `CREDB_BACKEND_PORT`
- `CREDB_FRONTEND_PORT`
- `CREDB_LMSTUDIO_BASE_URL`
- `CREDB_CRAWLER_MACHINE_ID`
- `CREDB_CRAWLER_SCAN_ROOTS`
- `CREDB_VECTOR_ENABLED`

## Development Order

1. scaffold project structure
2. build crawler MVP
3. add backend indexing and search
4. build frontend
5. add LM Studio integration

## Deployment Notes

- keep the system local-network friendly
- keep cloud dependencies optional
- keep deterministic search as the base path
- keep semantic search optional
