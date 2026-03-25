# Repository Rules

## Purpose

This document defines the repository governance rules for CredB Nexus. The goal is to keep the project stable while development moves from documentation into implementation.

## Branch Strategy

### Permanent Branches

- `main`

`main` should always represent the latest stable state of the repository.

### Working Branches

Use short-lived branches with clear prefixes:

- `docs/*`
- `crawler/*`
- `backend/*`
- `frontend/*`
- `ai/*`
- `db/*`
- `deployment/*`

Examples:

- `crawler/filesystem-traversal`
- `backend/search-api`
- `frontend/search-page`
- `ai/lmstudio-client`

## Branch Protection For `main`

Recommended rules:

- require pull requests before merging
- require at least 1 approving review
- dismiss stale reviews on new commits
- require conversation resolution before merge
- block force pushes
- block branch deletion

These rules should be applied using GitHub branch protection or rulesets.

## Pull Request Rules

Each pull request should:

- address one focused concern
- explain what changed
- explain why the change is needed
- include validation notes
- update related docs when behavior or architecture changes

Preferred merge strategy:

- squash merge

## Commit Rules

Use short imperative commit messages.

Good examples:

- `Add crawler metadata model`
- `Implement backend health endpoint`
- `Update wiki for Phase 1 foundation`

Avoid vague messages such as:

- `update`
- `changes`
- `final`

## Documentation Rules

Update documentation when any of the following change:

- repository structure
- architecture
- API shape
- deployment behavior
- development workflow

Primary files:

- `README.md`
- `docs/ARCHITECTURE.md`
- `docs/API_OVERVIEW.md`
- `docs/DEPLOYMENT.md`
- `docs/ROADMAP.md`
- `CHANGELOG.md`

## Technical Rules

- crawling must remain independent from LLM inference
- deterministic metadata retrieval must work without semantic search enabled
- semantic search must remain optional
- hidden and system file handling must be configurable
- multi-machine support should not be hardcoded into single-machine logic

## Labels

Recommended repository labels:

- `docs`
- `backend`
- `crawler`
- `frontend`
- `ai`
- `database`
- `architecture`
- `bug`
- `enhancement`
- `phase-1`
- `phase-2`
- `phase-3`
- `phase-4`
- `phase-5`
- `phase-6`
- `phase-7`
- `phase-8`

## Enforcement

Versioned policy files are stored in the repository, but GitHub-hosted protections must be applied through the GitHub UI or GitHub API.

Use:

- `.github/CODEOWNERS`
- `.github/PULL_REQUEST_TEMPLATE.md`
- `.github/ISSUE_TEMPLATE/`
- `scripts/setup-github-rules.ps1`
