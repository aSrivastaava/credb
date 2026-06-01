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

## Semantic Search — Cloudflare AI

Semantic search is routed through the Cloudflare Workers AI REST API using a free-tier model
(e.g. `@cf/baai/bge-small-en-v1.5` for embeddings, `@cf/meta/llama-3-8b-instruct` for summarisation).

Required environment variables for the backend:

```
CLOUDFLARE_ACCOUNT_ID=<your-account-id>
CLOUDFLARE_API_TOKEN=<your-api-token>
```

## Design Rules

- keep crawl execution asynchronous
- paginate list and search endpoints
- keep semantic search optional and non-blocking
- use metadata retrieval before LLM refinement
- never require a Cloudflare API token for basic keyword search
