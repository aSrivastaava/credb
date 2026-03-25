package com.credb.crawler.service;

import com.credb.crawler.config.CrawlerConfig;

public final class CrawlerAgentService {
    private final CrawlerConfig config;

    public CrawlerAgentService(CrawlerConfig config) {
        this.config = config;
    }

    public void start() {
        System.out.println("CredB crawler agent initialized.");
        System.out.println("Machine ID: " + config.machineId());
        System.out.println("Scan roots: " + config.scanRoots());
        System.out.println("Worker threads: " + config.workerThreads());
    }
}
