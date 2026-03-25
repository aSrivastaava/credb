package com.credb.crawler.config;

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

    private static String getEnv(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }
}
