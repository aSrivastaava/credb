# API Reference

## Overview

The CredB Nexus backend API should separate deterministic metadata retrieval from optional semantic processing.

## Core API Areas

- health
- machine registration
- crawl jobs
- search
- semantic search
- index statistics

## Endpoints

### Health

`GET /api/health`

Returns:

- service status
- dependency status
- basic readiness information

### Machines

`GET /api/machines`

Returns registered crawler agents.

`POST /api/machines/register`

Registers a crawler agent with the backend.

### Crawl Jobs

`POST /api/crawls`

Creates a crawl job.

Example request body:

```json
{
  "machineIds": ["lab-pc-01", "lab-pc-02"],
  "scanRoots": ["D:\\", "E:\\"],
  "priority": "normal"
}
```

`GET /api/crawls`

Returns crawl history.

`GET /api/crawls/{crawlId}`

Returns status for one crawl job.

Recommended statuses:

- `pending`
- `running`
- `completed`
- `failed`
- `partial`

### Search

`GET /api/search`

Supports metadata and keyword-based retrieval.

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

Runs semantic refinement or summarization over shortlisted records.

Example request body:

```json
{
  "query": "find suspicious python scripts modified recently",
  "machineIds": ["lab-pc-01"],
  "limit": 20
}
```

### Index Statistics

`GET /api/index/stats`

Returns:

- indexed record count
- machine coverage
- last crawl timestamp
- crawl volume summary

## API Rules

- keep crawl submission asynchronous
- paginate result-heavy endpoints
- separate raw search from semantic search
- keep AI optional and layered on top of retrieval

## Response Shape Guidance

A useful search response should include:

- machine identity
- file name
- full path
- extension
- size
- last modified
- directory flag

## Summary

The API should reflect the same system rule used everywhere else:

- deterministic retrieval first
- AI assistance second
