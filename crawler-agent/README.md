# Crawler Agent Module

This module will host the distributed crawler agent responsible for scanning assigned file systems and reporting metadata back to the backend.

## Planned Responsibilities

- traverse directories recursively
- extract file and directory metadata
- batch records efficiently
- report crawl progress and failures
- run safely on local machines in the network

## Suggested Stack

- Java
- ExecutorService for bounded concurrency

## Planned Metadata

- machine id
- crawl job id
- file name
- full path
- extension
- size
- last modified time
- directory flag
- access status

## Next Steps

1. define crawl job input model
2. implement local filesystem traversal
3. add batching and progress reporting
4. integrate with backend API
