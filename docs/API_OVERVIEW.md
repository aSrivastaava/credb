# API Overview

## Purpose

This document outlines the backend API shape that fits the CredB Nexus architecture. It is intentionally implementation-neutral so it can guide development before exact controller code exists.

## API Areas

- search
- crawl management
- machine registration
- health and status
- semantic query support

## Suggested Endpoints

### Health

`GET /api/health`

Returns service availability and basic dependency status.

### Machines

`GET /api/machines`

Returns known crawler agents and registration details.

`POST /api/machines/register`

Registers a crawler agent with the backend.

### Crawl Jobs

`POST /api/crawls`

Creates a new crawl job.

Suggested request body:

```json
{
  "machineIds": ["lab-pc-01", "lab-pc-02"],
  "scanRoots": ["D:\\", "E:\\"],
  "priority": "normal"
}
```

`GET /api/crawls`

Returns crawl job history.

`GET /api/crawls/{crawlId}`

Returns status and summary for a specific crawl job.

### Search

`GET /api/search`

Performs metadata and keyword-based retrieval.

Suggested query parameters:

- `q`
- `machineId`
- `extension`
- `pathPrefix`
- `minSize`
- `maxSize`
- `modifiedAfter`
- `modifiedBefore`
- `page`
- `pageSize`

### Semantic Search

`POST /api/search/semantic`

Performs LLM-assisted or vector-assisted refinement over shortlisted records.

Suggested request body:

```json
{
  "query": "find suspicious python scripts modified recently",
  "machineIds": ["lab-pc-01"],
  "limit": 20
}
```

### Index Statistics

`GET /api/index/stats`

Returns basic index metrics such as record count, machine coverage, and last crawl times.

## Response Shape Guidance

Search results should return both raw metadata and enough context for the UI to render useful summaries.

Suggested fields:

```json
{
  "items": [
    {
      "machineId": "lab-pc-01",
      "fileName": "script.py",
      "fullPath": "D:\\Users\\Public\\script.py",
      "extension": ".py",
      "sizeBytes": 2842,
      "lastModified": "2026-03-25T10:15:00Z",
      "isDirectory": false
    }
  ],
  "page": 1,
  "pageSize": 20,
  "total": 1
}
```

## API Design Rules

- keep search endpoints deterministic by default
- separate raw search from semantic search
- keep crawl submission asynchronous
- return machine-readable crawl states
- paginate all list and search results

## Recommended Crawl Status Values

- `pending`
- `running`
- `completed`
- `failed`
- `partial`

## Summary

The API should reflect the same architectural rule as the rest of the system:

- deterministic retrieval first
- AI assistance second
