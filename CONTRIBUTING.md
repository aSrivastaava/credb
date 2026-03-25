# Contributing

## Scope

This repository currently contains the documentation baseline for CredB Nexus. Implementation will be added incrementally. Contributions should preserve the project direction:

- offline-first operation
- deterministic indexing
- selective AI integration
- modular architecture

## Ways To Contribute

- improve documentation
- refine architecture decisions
- add backend, crawler, or frontend implementation
- report bugs or unclear requirements
- propose performance or deployment improvements

## Ground Rules

- keep changes focused
- avoid unrelated refactors
- document architectural tradeoffs clearly
- do not mix crawl logic and LLM inference in the same execution path unless there is a strong reason
- keep semantic search optional, not a hard dependency

## Before Opening A Pull Request

- read the main [README](./README.md)
- review [Architecture](./docs/ARCHITECTURE.md)
- review [Roadmap](./docs/ROADMAP.md)
- confirm the change fits the project direction

## Branching Guidance

- use short descriptive branch names
- keep one concern per branch
- prefer small reviewable pull requests

Examples:

- `docs/architecture-update`
- `backend/search-api`
- `crawler/batch-indexing`

## Pull Request Expectations

Each pull request should include:

- a clear summary of what changed
- the reason for the change
- any architectural impact
- test notes, if implementation is involved

## Documentation Expectations

When changing architecture, API shape, or deployment behavior, update the related files:

- [README.md](./README.md)
- [docs/ARCHITECTURE.md](./docs/ARCHITECTURE.md)
- [docs/API_OVERVIEW.md](./docs/API_OVERVIEW.md)
- [docs/DEPLOYMENT.md](./docs/DEPLOYMENT.md)
- [wiki/](./wiki/)

## Coding Expectations

When code is added:

- keep modules separated by responsibility
- prefer clear naming over clever abstractions
- design for local-network deployment first
- keep logging useful and structured
- keep configuration externalized through environment variables or config files

## Issue Reporting

When reporting a bug or design problem, include:

- expected behavior
- actual behavior
- environment details
- reproduction steps
- logs or screenshots when relevant

## Discussions

Use issues for:

- bugs
- feature proposals
- architecture questions
- deployment concerns

## License

By contributing, you agree that your contributions will be licensed under the repository license.
