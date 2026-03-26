package com.credb.crawler.service;

import com.credb.crawler.config.CrawlerConfig;
import com.credb.crawler.model.CrawlSummary;
import com.credb.crawler.model.FileRecord;

public final class CrawlerAgentService {
    private final CrawlerConfig config;
    private final FileSystemTraversalService traversalService;

    public CrawlerAgentService(CrawlerConfig config) {
        this.config = config;
        this.traversalService = new FileSystemTraversalService();
    }

    public void start() {
        System.out.println("CredB crawler agent initialized.");
        System.out.println("Machine ID: " + config.machineId());
        System.out.println("Starting points: " + config.resolvedScanRoots());
        System.out.println("Worker threads: " + config.workerThreads());
        System.out.println("Verbose mode: " + config.verbose());
        System.out.println("Include hidden: " + config.includeHidden());
        System.out.println("Output limit: " + (config.outputLimit() == null ? "none" : config.outputLimit()));

        CrawlSummary summary = traversalService.crawl(config);

        System.out.println("Scanned roots: " + summary.scannedRoots());
        System.out.println("Visible directories: " + summary.visibleDirectories());
        System.out.println("Visible files: " + summary.visibleFiles());
        System.out.println("Hidden directories detected: " + summary.hiddenDirectories());
        System.out.println("Hidden files detected: " + summary.hiddenFiles());
        System.out.println("Hidden entries skipped: " + summary.skippedHiddenEntries());
        System.out.println("Protected or inaccessible paths: " + summary.inaccessiblePaths());
        System.out.println("Total discovered entries: " + summary.records().size());

        if (config.verbose()) {
            System.out.println("Discovered entries:");
            summary.records().stream()
                    .limit(config.outputLimit() == null ? Long.MAX_VALUE : config.outputLimit())
                    .forEach(this::printRecord);
        }
    }

    private void printRecord(FileRecord record) {
        System.out.printf(
                "[%s] %s (%s%s)%n",
                record.directory() ? "DIR" : "FILE",
                record.fullPath(),
                record.accessStatus(),
                record.hidden() ? ", hidden" : ""
        );
    }
}
