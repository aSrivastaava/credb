# Backend Module

This module will host the CredB Nexus central backend service.

## Planned Responsibilities

- expose search APIs
- manage crawl jobs
- register and track crawler agents
- query the metadata database
- call LM Studio for semantic refinement

## Suggested Stack

- Java
- Spring Boot
- PostgreSQL or SQLite

## Planned Internal Areas

- `search`
- `crawl`
- `machine`
- `index`
- `ai`

## Next Steps

1. initialize Spring Boot project structure
2. define metadata entities and repositories
3. implement health and crawl endpoints
4. add search service and filtering
