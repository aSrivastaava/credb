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

## Roadmap Automation

The roadmap is generated from `project-status.json`.

Update the repository roadmap locally:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\update-roadmap.ps1
```

Update the repository roadmap and a cloned wiki `Roadmap.md` together if you want to test locally:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\update-roadmap.ps1 -WikiRoadmapFile .\path\to\wiki\Roadmap.md
```

## Automation Options

The primary automation path is GitHub Actions.

Practical options:

- run the PowerShell script manually when phase status changes
- use a GitHub Action to sync the wiki roadmap when `project-status.json` changes on `main`

Important limitation:

- the wiki is a separate Git repository, so automation needs permission to clone, commit, and push to the wiki repo

## GitHub Action

The repository includes [`.github/workflows/sync-wiki-roadmap.yml`](../.github/workflows/sync-wiki-roadmap.yml) to update the wiki `Roadmap.md` automatically on pushes to `main` that modify:

- `project-status.json`
- `scripts/update-roadmap.ps1`

This workflow uses `GITHUB_TOKEN` and expects the repository Actions permissions to allow content writes.

The repository roadmap file should be updated in the same commit as `project-status.json`, or regenerated locally before pushing.

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
