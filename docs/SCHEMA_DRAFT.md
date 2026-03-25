# Schema Draft

## Purpose

This document defines the initial relational schema direction for CredB Nexus. It is intentionally conservative and optimized for deterministic indexing first.

## Core Tables

### machines

Represents crawler agents or scanned hosts.

Suggested fields:

- `id`
- `machine_id`
- `hostname`
- `ip_address`
- `status`
- `last_seen_at`
- `created_at`
- `updated_at`

### crawl_jobs

Represents requested crawl operations.

Suggested fields:

- `id`
- `job_uuid`
- `requested_by`
- `scope_type`
- `status`
- `started_at`
- `completed_at`
- `created_at`
- `updated_at`

### crawl_job_targets

Maps crawl jobs to machines and scan roots.

Suggested fields:

- `id`
- `crawl_job_id`
- `machine_id`
- `scan_root`
- `status`
- `error_message`
- `created_at`
- `updated_at`

### indexed_entries

Primary metadata table for files and directories.

Suggested fields:

- `id`
- `machine_id`
- `crawl_job_id`
- `entry_type`
- `file_name`
- `full_path`
- `parent_path`
- `extension`
- `size_bytes`
- `last_modified_at`
- `created_at_fs`
- `is_hidden`
- `access_status`
- `content_type`
- `file_hash`
- `indexed_at`

## Minimum Indexing Set

The minimum fields required for an MVP search experience are:

- `machine_id`
- `file_name`
- `full_path`
- `extension`
- `size_bytes`
- `last_modified_at`
- `entry_type`
- `indexed_at`

## Search-Oriented Index Suggestions

- index on `machine_id`
- index on `file_name`
- index on `extension`
- index on `last_modified_at`
- index on `full_path`

For PostgreSQL, consider:

- trigram or text search support for file names and paths
- composite indexes on common filter combinations

## Notes

- hidden files should be policy-driven, not automatically excluded
- file hashing can be optional in early versions due to cost
- content extraction should remain a later phase
- embeddings should not be required for the base schema
