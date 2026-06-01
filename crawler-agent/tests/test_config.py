from pathlib import Path

import pytest

from crawler_agent.config import CrawlerConfig


def test_defaults_from_environment(monkeypatch):
    monkeypatch.delenv("CREDB_CRAWLER_MACHINE_ID", raising=False)
    monkeypatch.delenv("CREDB_CRAWLER_SCAN_ROOTS", raising=False)
    monkeypatch.delenv("CREDB_CRAWLER_WORKER_THREADS", raising=False)
    cfg = CrawlerConfig.from_environment([])
    assert cfg.machine_id == "local-dev-machine"
    assert cfg.worker_threads == 4
    assert cfg.verbose is False
    assert cfg.include_hidden is False
    assert cfg.output_limit is None


def test_env_overrides(monkeypatch):
    monkeypatch.setenv("CREDB_CRAWLER_MACHINE_ID", "test-machine")
    monkeypatch.setenv("CREDB_CRAWLER_SCAN_ROOTS", "/tmp,/var")
    monkeypatch.setenv("CREDB_CRAWLER_WORKER_THREADS", "8")
    cfg = CrawlerConfig.from_environment([])
    assert cfg.machine_id == "test-machine"
    assert cfg.worker_threads == 8


def test_cli_verbose_flag():
    cfg = CrawlerConfig.from_environment(["--verbose"])
    assert cfg.verbose is True


def test_cli_short_verbose_flag():
    cfg = CrawlerConfig.from_environment(["-v"])
    assert cfg.verbose is True


def test_cli_include_hidden():
    cfg = CrawlerConfig.from_environment(["--include-hidden"])
    assert cfg.include_hidden is True


def test_cli_limit():
    cfg = CrawlerConfig.from_environment(["--limit", "50"])
    assert cfg.output_limit == 50


def test_cli_limit_short():
    cfg = CrawlerConfig.from_environment(["-l", "10"])
    assert cfg.output_limit == 10


def test_cli_root_override():
    cfg = CrawlerConfig.from_environment(["--root", "/tmp"])
    assert "/tmp" in cfg.scan_roots


def test_cli_root_equals_form():
    cfg = CrawlerConfig.from_environment(["--root=/tmp"])
    assert "/tmp" in cfg.scan_roots


def test_cli_unknown_arg_raises():
    with pytest.raises(ValueError):
        CrawlerConfig.from_environment(["--unknown-flag"])


def test_resolved_scan_roots_comma():
    cfg = CrawlerConfig.from_environment([])
    cfg.scan_roots = "/tmp,/var"
    roots = cfg.resolved_scan_roots()
    assert Path("/tmp") in roots
    assert Path("/var") in roots


def test_resolved_scan_roots_semicolon():
    cfg = CrawlerConfig.from_environment([])
    cfg.scan_roots = "/tmp;/var"
    roots = cfg.resolved_scan_roots()
    assert Path("/tmp") in roots
    assert Path("/var") in roots
