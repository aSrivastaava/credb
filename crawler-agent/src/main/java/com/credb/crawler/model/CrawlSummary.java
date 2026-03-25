package com.credb.crawler.model;

import java.util.List;

public record CrawlSummary(
        int scannedRoots,
        int directories,
        int files,
        int inaccessiblePaths,
        List<FileRecord> records
) {
}
