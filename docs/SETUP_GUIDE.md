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

Update the repository roadmap:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\update-roadmap.ps1
```

Update the repository roadmap and a cloned wiki `Roadmap.md` together:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\update-roadmap.ps1 -WikiRoadmapFile .\path\to\wiki\Roadmap.md
```

Use the wiki sync helper to clone or pull the wiki repo, regenerate `Roadmap.md`, and commit it locally:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\sync-wiki-roadmap.ps1
```

Add `-Push` to also push the wiki update:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\sync-wiki-roadmap.ps1 -Push
```

## Automation Options

You do not have to run the roadmap script by hand every time, but some trigger still has to invoke it.

Practical options:

- run the PowerShell script manually when phase status changes
- use a local Git hook such as `post-commit` or `pre-push`
- use a GitHub Action to regenerate the repo roadmap automatically

### Local Automatic Repo Updates

This repository includes a versioned Git hook setup for automatic roadmap regeneration before commits.

Enable it once:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\install-git-hooks.ps1
```

After that, the pre-commit hook will regenerate `docs/ROADMAP.md` from `project-status.json` and stage it automatically.

Important limitation:

- the wiki is a separate Git repository, so wiki updates still need a workflow or script with permission to clone, commit, and push to the wiki repo

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
