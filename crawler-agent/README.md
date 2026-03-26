# Crawler Agent

This module will host the CredB Nexus crawler agent.

## Planned Responsibilities

- recursive filesystem traversal
- metadata extraction
- crawl progress reporting
- batched submission to backend services

## Planned Stack

- Java
- bounded concurrency with ExecutorService

## Current Module Layout

- `pom.xml`
- `src/main/java/com/credb/crawler/`
- `src/main/java/com/credb/crawler/config/`
- `src/main/java/com/credb/crawler/model/`
- `src/main/java/com/credb/crawler/service/`

## Current Status

The crawler module now has:

- a Maven project file
- an application entry point
- an environment-backed config model
- a placeholder crawl job model
- a service entry point for traversal logic
- recursive filesystem traversal using Java NIO
- basic file and directory metadata capture
- crawl summary output to the console
- machine identity using MAC-based `machineId` and computer name

## Runtime Options

- `--verbose` or `-v`
  - print discovered entries
- `--limit <n>` or `-l <n>`
  - limit printed entries in verbose mode
- `--root <path>` or `--start <path>` or `-s <path>`
  - override the crawl starting point for the current run
- `--include-hidden` or `--hidden`
  - include hidden files and folders in traversal output
- `--output <file>` or `-o <file>`
  - export crawl summary and records to a JSON file

Recommended temporary output path:

- `data/crawl-output.json`

Without verbose mode, the crawler prints summary counts only.
By default, hidden files and folders are detected and counted but skipped during traversal.
Configured starting roots are still entered even if the operating system marks them hidden or system-protected.
The configured starting root itself is not counted as a visible folder total.

## Storage Direction

Current stage:

- export to a temporary local JSON file for inspection and handoff

Planned next stage:

- persist crawler output into PostgreSQL on the local Ubuntu server
- add pgvector later for semantic retrieval and RAG workflows
