package com.credb.crawler.model;

import java.util.List;

public record CrawlSummary(
        int scannedRoots,
        int visibleDirectories,
        int visibleFiles,
        int hiddenDirectories,
        int hiddenFiles,
        int skippedHiddenEntries,
        int inaccessiblePaths,
        List<FileRecord> records
) {
}
