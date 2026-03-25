package com.credb.crawler.model;

public record CrawlJob(
        String jobId,
        String machineId,
        String scanRoot
) {
}
