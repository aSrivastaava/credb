# Deployment Notes

CredB Nexus is intended for local-network and offline-friendly deployment.

## Prototype Shape

- one backend instance
- one crawler instance
- one frontend instance
- local database
- optional local LM Studio

## Multi-Machine Shape

- central backend
- distributed crawler agents
- central metadata database
- frontend connected to backend
- optional local LLM integration on the backend host

## Deployment Principles

- keep crawlers close to scanned file systems
- avoid cloud dependency
- keep semantic search optional
- prefer deterministic search as the default path
