package com.credb.crawler.config;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public record CrawlerConfig(
        String machineId,
        String scanRoots,
        int workerThreads
) {
    public static CrawlerConfig fromEnvironment() {
        String machineId = getEnv("CREDB_CRAWLER_MACHINE_ID", "local-dev-machine");
        String scanRoots = getEnv("CREDB_CRAWLER_SCAN_ROOTS", "D:\\");
        int workerThreads = Integer.parseInt(getEnv("CREDB_CRAWLER_WORKER_THREADS", "4"));

        return new CrawlerConfig(machineId, scanRoots, workerThreads);
    }

    public List<Path> resolvedScanRoots() {
        return Arrays.stream(scanRoots.split("[;,]"))
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .map(Path::of)
                .toList();
    }

    private static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
