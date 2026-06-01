from __future__ import annotations

from dataclasses import dataclass, field
from datetime import datetime


@dataclass
class FileRecord:
    machine_id: str
    full_path: str
    file_name: str
    directory: bool
    hidden: bool
    size_bytes: int
    last_modified_at: datetime | None
    access_status: str


@dataclass
class CrawlJob:
    job_id: str
    machine_id: str
    scan_root: str


@dataclass
class CrawlSummary:
    scanned_roots: int
    visible_directories: int
    visible_files: int
    hidden_directories: int
    hidden_files: int
    skipped_hidden_entries: int
    inaccessible_paths: int
    records: list[FileRecord] = field(default_factory=list)
