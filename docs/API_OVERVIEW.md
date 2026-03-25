# API Overview

The backend API should separate deterministic search from optional semantic processing.

## Planned Areas

- health
- crawl jobs
- machine registration
- metadata search
- semantic search

## Planned Endpoints

- `GET /api/health`
- `POST /api/crawls`
- `GET /api/crawls`
- `GET /api/crawls/{id}`
- `GET /api/machines`
- `POST /api/machines/register`
- `GET /api/search`
- `POST /api/search/semantic`

## Design Rules

- keep crawl execution asynchronous
- paginate list and search endpoints
- keep semantic search optional
- use metadata retrieval before LLM refinement
