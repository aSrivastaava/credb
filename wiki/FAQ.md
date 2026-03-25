# FAQ

## What is CredB Nexus?

CredB Nexus is an offline-first distributed file indexing and retrieval platform designed for local networks. It helps search and inspect files across multiple machines from one central interface.

## Why not just use normal file search?

Normal file search usually works on one system at a time. CredB Nexus is meant to centralize file metadata across multiple machines and add structured filtering plus optional semantic search.

## Why use Java for the crawler?

Java is a practical choice for a multi-threaded crawler with strong filesystem libraries, good concurrency support, and straightforward integration with a Spring Boot backend.

## Why use Spring Boot?

Spring Boot is a strong fit for REST APIs, crawl coordination, service-layer logic, and integration with databases and local AI services.

## Why should the crawler and LLM be separated?

Because they solve different problems.

- crawling should be fast and deterministic
- LLM processing should be selective and on-demand

If you mix them directly, crawling becomes slower, more expensive, and harder to debug.

## Should the LLM process every file?

No. That is usually a bad design.

The better pattern is:

1. retrieve candidates from the database
2. send only top results to the LLM
3. use the LLM for explanation, ranking, or semantic interpretation

## Should I use SQLite or PostgreSQL?

- use `SQLite` for an early single-machine prototype
- use `PostgreSQL` for multi-machine or production-style deployment

## Do I need a vector database from the start?

No. Start with metadata indexing and keyword search. Add embeddings and vector search only after the deterministic search pipeline is stable.

## Is FAISS required?

No. It is one possible option for local vector search, but it should remain optional in early versions.

## Can this work without internet access?

Yes. The architecture is designed for offline or local-network-only operation, including local LLM use through LM Studio.
