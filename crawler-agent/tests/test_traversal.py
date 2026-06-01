from pathlib import Path

from crawler_agent.config import CrawlerConfig
from crawler_agent.services.traversal import FileSystemTraversalService


def _config(tmp_path: Path, **kwargs) -> CrawlerConfig:
    cfg = CrawlerConfig.from_environment([])
    cfg.scan_roots = str(tmp_path)
    for k, v in kwargs.items():
        object.__setattr__(cfg, k, v)
    return cfg


def test_counts_visible_files(tmp_path):
    (tmp_path / "a.txt").write_text("hello")
    (tmp_path / "b.txt").write_text("world")
    cfg = _config(tmp_path)
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.visible_files == 2
    assert summary.scanned_roots == 1


def test_counts_visible_directories(tmp_path):
    (tmp_path / "subdir").mkdir()
    (tmp_path / "subdir" / "file.txt").write_text("x")
    cfg = _config(tmp_path)
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.visible_directories == 1
    assert summary.visible_files == 1


def test_hidden_files_skipped_by_default(tmp_path):
    (tmp_path / "visible.txt").write_text("v")
    (tmp_path / ".hidden.txt").write_text("h")
    cfg = _config(tmp_path)
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.visible_files == 1
    assert summary.hidden_files == 1
    assert summary.skipped_hidden_entries == 1


def test_hidden_files_included_when_flag_set(tmp_path):
    (tmp_path / "visible.txt").write_text("v")
    (tmp_path / ".hidden.txt").write_text("h")
    cfg = _config(tmp_path, include_hidden=True)
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.visible_files == 1
    assert summary.hidden_files == 1
    assert summary.skipped_hidden_entries == 0


def test_hidden_directory_skipped_by_default(tmp_path):
    hidden_dir = tmp_path / ".hidden_dir"
    hidden_dir.mkdir()
    (hidden_dir / "inside.txt").write_text("x")
    cfg = _config(tmp_path)
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.visible_files == 0
    assert summary.hidden_directories == 1
    assert summary.skipped_hidden_entries == 1


def test_nonexistent_root_counts_as_inaccessible():
    cfg = CrawlerConfig.from_environment([])
    cfg.scan_roots = "/this/path/does/not/exist"
    summary = FileSystemTraversalService().crawl(cfg)
    assert summary.inaccessible_paths == 1
    assert summary.scanned_roots == 0


def test_records_contain_machine_id(tmp_path):
    (tmp_path / "file.txt").write_text("data")
    cfg = _config(tmp_path, machine_id="test-machine")
    summary = FileSystemTraversalService().crawl(cfg)
    file_records = [r for r in summary.records if not r.directory]
    assert all(r.machine_id == "test-machine" for r in file_records)


def test_file_record_has_size(tmp_path):
    (tmp_path / "file.txt").write_text("hello")
    cfg = _config(tmp_path)
    summary = FileSystemTraversalService().crawl(cfg)
    file_records = [r for r in summary.records if not r.directory]
    assert file_records[0].size_bytes == 5
