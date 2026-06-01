from __future__ import annotations

from crawler_agent.config import CrawlerConfig
from crawler_agent.models import FileRecord
from crawler_agent.services.traversal import FileSystemTraversalService


class CrawlerAgentService:
    def __init__(self, config: CrawlerConfig) -> None:
        self.config = config
        self._traversal = FileSystemTraversalService()

    def start(self) -> None:
        cfg = self.config
        print("CredB crawler agent initialized.")
        print(f"Machine ID: {cfg.machine_id}")
        print(f"Starting points: {cfg.resolved_scan_roots()}")
        print(f"Worker threads: {cfg.worker_threads}")
        print(f"Verbose mode: {cfg.verbose}")
        print(f"Include hidden: {cfg.include_hidden}")
        print(f"Output limit: {'none' if cfg.output_limit is None else cfg.output_limit}")

        summary = self._traversal.crawl(cfg)

        print(f"Scanned roots: {summary.scanned_roots}")
        print(f"Visible directories: {summary.visible_directories}")
        print(f"Visible files: {summary.visible_files}")
        print(f"Hidden directories detected: {summary.hidden_directories}")
        print(f"Hidden files detected: {summary.hidden_files}")
        print(f"Hidden entries skipped: {summary.skipped_hidden_entries}")
        print(f"Protected or inaccessible paths: {summary.inaccessible_paths}")
        print(f"Total discovered entries: {len(summary.records)}")

        if cfg.verbose:
            print("Discovered entries:")
            records = summary.records
            if cfg.output_limit is not None:
                records = records[: cfg.output_limit]
            for record in records:
                self._print_record(record)

    def _print_record(self, record: FileRecord) -> None:
        kind = "DIR" if record.directory else "FILE"
        hidden_tag = ", hidden" if record.hidden else ""
        print(f"[{kind}] {record.full_path} ({record.access_status}{hidden_tag})")
