from __future__ import annotations

import os
import sys
from dataclasses import dataclass
from pathlib import Path


@dataclass
class CrawlerConfig:
    machine_id: str
    scan_roots: str
    worker_threads: int
    verbose: bool
    output_limit: int | None
    include_hidden: bool

    def resolved_scan_roots(self) -> list[Path]:
        parts = [p.strip() for p in self.scan_roots.replace(";", ",").split(",")]
        return [Path(p) for p in parts if p]

    @classmethod
    def from_environment(cls, args: list[str] | None = None) -> CrawlerConfig:
        if args is None:
            args = sys.argv[1:]

        machine_id = os.environ.get("CREDB_CRAWLER_MACHINE_ID", "local-dev-machine")
        scan_roots = os.environ.get("CREDB_CRAWLER_SCAN_ROOTS", "/")
        worker_threads = int(os.environ.get("CREDB_CRAWLER_WORKER_THREADS", "4"))
        verbose = False
        output_limit: int | None = None
        include_hidden = False

        i = 0
        while i < len(args):
            arg = args[i].lower()
            if arg in ("--verbose", "-v"):
                verbose = True
            elif arg in ("--include-hidden", "--hidden"):
                include_hidden = True
            elif arg in ("--root", "--start", "-s"):
                i += 1
                scan_roots = args[i]
            elif arg in ("--limit", "-l"):
                i += 1
                output_limit = int(args[i])
            elif arg.startswith("--root=") or arg.startswith("--start="):
                scan_roots = args[i].split("=", 1)[1]
            elif arg.startswith("--limit="):
                output_limit = int(args[i].split("=", 1)[1])
            elif arg == "--include-hidden=true":
                include_hidden = True
            else:
                raise ValueError(f"Unknown argument: {args[i]}")
            i += 1

        return cls(
            machine_id=machine_id,
            scan_roots=scan_roots,
            worker_threads=worker_threads,
            verbose=verbose,
            output_limit=output_limit,
            include_hidden=include_hidden,
        )
