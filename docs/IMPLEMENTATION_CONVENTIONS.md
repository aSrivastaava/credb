# Implementation Conventions

## Purpose

These conventions keep the project consistent as code is added.

## General

- keep modules separated by responsibility
- prefer explicit names
- favor simple interfaces over early abstraction
- keep configuration outside source code

## Backend

- separate controller, service, and persistence concerns
- keep search deterministic by default
- isolate LM Studio integration behind one service boundary

## Crawler

- use bounded concurrency
- batch writes or API submissions
- treat access-denied paths as expected outcomes
- avoid expensive per-file work in the hot path

## Frontend

- keep state boundaries clear
- build search and crawl views first
- avoid coupling UI rendering to AI-specific assumptions

## Data

- start with metadata-first indexing
- add hashing selectively
- add embeddings only after core retrieval is stable

## Observability

- use structured logs
- log crawl failures with enough context to debug
- avoid logging sensitive content unnecessarily
