# Repository Rules

## Branch Strategy

- `main` is the stable branch
- use short-lived feature branches
- do not develop directly on `main`

Recommended prefixes:

- `docs/*`
- `crawler/*`
- `backend/*`
- `frontend/*`
- `ai/*`
- `db/*`

## Pull Request Rules

- one focused change per PR
- explain what changed and why
- include validation notes
- update docs when behavior changes

## Commit Rules

Use short imperative commit messages.

Examples:

- `Add initial crawler model`
- `Create backend health endpoint`
- `Update repository rules`

## Technical Rules

- crawling and LLM execution must stay separate
- deterministic search must work without semantic search
- semantic features should remain optional
- hidden/system file handling should be configurable
