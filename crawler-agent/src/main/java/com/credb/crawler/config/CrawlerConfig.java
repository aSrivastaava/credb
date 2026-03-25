package com.credb.crawler.config;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public record CrawlerConfig(
        String machineId,
        String scanRoots,
        int workerThreads,
        boolean verbose,
        Integer outputLimit,
        boolean includeHidden
) {
    public static CrawlerConfig fromEnvironment(String[] args) {
        String machineId = getEnv("CREDB_CRAWLER_MACHINE_ID", "local-dev-machine");
        String scanRoots = getEnv("CREDB_CRAWLER_SCAN_ROOTS", "D:\\");
        int workerThreads = Integer.parseInt(getEnv("CREDB_CRAWLER_WORKER_THREADS", "4"));
        boolean verbose = false;
        Integer outputLimit = null;
        boolean includeHidden = false;

        for (int index = 0; index < args.length; index++) {
            String arg = args[index];
            String normalized = arg.toLowerCase(Locale.ROOT);

            switch (normalized) {
                case "--verbose", "-v" -> verbose = true;
                case "--include-hidden", "--hidden" -> includeHidden = true;
                case "--root", "--start", "-s" -> {
                    if (index + 1 >= args.length) {
                        throw new IllegalArgumentException("Missing value for " + arg);
                    }
                    scanRoots = args[++index];
                }
                case "--limit", "-l" -> {
                    if (index + 1 >= args.length) {
                        throw new IllegalArgumentException("Missing value for " + arg);
                    }
                    outputLimit = Integer.parseInt(args[++index]);
                }
                default -> {
                    if (normalized.startsWith("--root=") || normalized.startsWith("--start=")) {
                        scanRoots = arg.substring(arg.indexOf('=') + 1);
                    } else if (normalized.startsWith("--limit=")) {
                        outputLimit = Integer.parseInt(arg.substring(arg.indexOf('=') + 1));
                    } else if (normalized.equals("--include-hidden=true")) {
                        includeHidden = true;
                    } else {
                        throw new IllegalArgumentException("Unknown argument: " + arg);
                    }
                }
            }
        }

        return new CrawlerConfig(machineId, scanRoots, workerThreads, verbose, outputLimit, includeHidden);
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
