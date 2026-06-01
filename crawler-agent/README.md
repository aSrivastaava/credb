# Crawler Agent

This module will host the CredB Nexus crawler agent.

## Planned Responsibilities

- recursive filesystem traversal
- metadata extraction
- crawl progress reporting
- batched submission to backend services

## Stack

- Python 3.11+
- stdlib only (`os`, `pathlib`, `datetime`) — no external runtime dependencies
- bounded concurrency via `concurrent.futures` (planned for Phase 3)

## Module Layout

```
crawler-agent/
├── main.py
├── pyproject.toml
├── requirements-dev.txt
└── crawler_agent/
    ├── config.py        # env + CLI config
    ├── models.py        # FileRecord, CrawlJob, CrawlSummary
    └── services/
        ├── traversal.py # filesystem walk logic
        └── crawler.py   # orchestrator + output
```

## Current Status

The crawler module now has:

- a `pyproject.toml` project file
- an application entry point (`main.py`)
- an environment-backed config model
- dataclass models for FileRecord, CrawlJob, CrawlSummary
- a service entry point for traversal logic
- recursive filesystem traversal using `os.walk` + `pathlib`
- basic file and directory metadata capture
- crawl summary output to the console

## Runtime Options

- `--verbose` or `-v`
  - print discovered entries
- `--limit <n>` or `-l <n>`
  - limit printed entries in verbose mode
- `--root <path>` or `--start <path>` or `-s <path>`
  - override the crawl starting point for the current run
- `--include-hidden` or `--hidden`
  - include hidden files and folders in traversal output

Without verbose mode, the crawler prints summary counts only.
By default, hidden files and folders are detected and counted but skipped during traversal.
Configured starting roots are still entered even if the operating system marks them hidden or system-protected.
The configured starting root itself is not counted as a visible folder total.
