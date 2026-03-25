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
        System.out.println("Scan roots: " + config.resolvedScanRoots());
        System.out.println("Worker threads: " + config.workerThreads());

        CrawlSummary summary = traversalService.crawl(config);

        System.out.println("Scanned roots: " + summary.scannedRoots());
        System.out.println("Directories: " + summary.directories());
        System.out.println("Files: " + summary.files());
        System.out.println("Inaccessible paths: " + summary.inaccessiblePaths());
        System.out.println("Discovered entries:");

        summary.records().forEach(this::printRecord);
    }

    private void printRecord(FileRecord record) {
        System.out.printf(
                "[%s] %s (%s)%n",
                record.directory() ? "DIR" : "FILE",
                record.fullPath(),
                record.accessStatus()
        );
    }
}
