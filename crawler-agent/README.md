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

## Runtime Options

- `--verbose` or `-v`
  - print discovered entries
- `--limit <n>` or `-l <n>`
  - limit printed entries in verbose mode
- `--root <path>` or `--start <path>` or `-s <path>`
  - override the crawl starting point for the current run

Without verbose mode, the crawler prints summary counts only.
