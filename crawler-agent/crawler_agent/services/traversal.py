from __future__ import annotations

import os
from datetime import datetime, timezone
from pathlib import Path

from crawler_agent.config import CrawlerConfig
from crawler_agent.models import CrawlSummary, FileRecord


class FileSystemTraversalService:
    def crawl(self, config: CrawlerConfig) -> CrawlSummary:
        records: list[FileRecord] = []
        scanned_roots = 0
        visible_directories = 0
        visible_files = 0
        hidden_directories = 0
        hidden_files = 0
        skipped_hidden_entries = 0
        inaccessible_paths = 0

        configured_roots = set()
        for root in config.resolved_scan_roots():
            try:
                configured_roots.add(root.resolve())
            except OSError:
                pass

        for root in config.resolved_scan_roots():
            if not root.exists():
                inaccessible_paths += 1
                continue

            try:
                normalized_root = root.resolve()
            except OSError:
                inaccessible_paths += 1
                continue

            try:
                for dirpath_str, dirnames, filenames in os.walk(normalized_root, topdown=True):
                    dirpath = Path(dirpath_str)
                    is_root = dirpath.resolve() in configured_roots

                    if not is_root:
                        hidden = self._is_hidden(dirpath)
                        if hidden:
                            hidden_directories += 1
                            if not config.include_hidden:
                                skipped_hidden_entries += 1
                                dirnames.clear()
                                continue
                        else:
                            visible_directories += 1

                        record = self._make_record(config.machine_id, dirpath, is_dir=True)
                        if record:
                            records.append(record)
                        else:
                            inaccessible_paths += 1
                            dirnames.clear()
                            continue
                    else:
                        record = self._make_record(config.machine_id, dirpath, is_dir=True)
                        if record:
                            records.append(record)

                    to_remove = []
                    for d in dirnames:
                        child = dirpath / d
                        if child.resolve() in configured_roots:
                            continue
                        if self._is_hidden(child):
                            hidden_directories += 1
                            if not config.include_hidden:
                                skipped_hidden_entries += 1
                                to_remove.append(d)
                    for d in to_remove:
                        dirnames.remove(d)

                    for filename in filenames:
                        filepath = dirpath / filename
                        hidden = self._is_hidden(filepath)
                        if hidden:
                            hidden_files += 1
                            if not config.include_hidden:
                                skipped_hidden_entries += 1
                                continue
                        else:
                            visible_files += 1

                        record = self._make_record(config.machine_id, filepath, is_dir=False)
                        if record:
                            records.append(record)
                        else:
                            inaccessible_paths += 1

                scanned_roots += 1
            except PermissionError:
                inaccessible_paths += 1

        return CrawlSummary(
            scanned_roots=scanned_roots,
            visible_directories=visible_directories,
            visible_files=visible_files,
            hidden_directories=hidden_directories,
            hidden_files=hidden_files,
            skipped_hidden_entries=skipped_hidden_entries,
            inaccessible_paths=inaccessible_paths,
            records=records,
        )

    def _make_record(self, machine_id: str, path: Path, is_dir: bool) -> FileRecord | None:
        try:
            stat = path.stat()
            last_modified = datetime.fromtimestamp(stat.st_mtime, tz=timezone.utc)
            size = 0 if is_dir else stat.st_size
            return FileRecord(
                machine_id=machine_id,
                full_path=str(path.resolve()),
                file_name=path.name or str(path),
                directory=is_dir,
                hidden=self._is_hidden(path),
                size_bytes=size,
                last_modified_at=last_modified,
                access_status="accessible",
            )
        except (PermissionError, OSError):
            return FileRecord(
                machine_id=machine_id,
                full_path=str(path),
                file_name=path.name or str(path),
                directory=is_dir,
                hidden=False,
                size_bytes=0,
                last_modified_at=None,
                access_status="inaccessible",
            )

    def _is_hidden(self, path: Path) -> bool:
        return path.name.startswith(".")
