package com.credb.crawler.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public record CrawlerConfig(
        String machineId,
        String machineName,
        String scanRoots,
        int workerThreads,
        boolean verbose,
        Integer outputLimit,
        boolean includeHidden,
        String outputFile
) {
    public static CrawlerConfig fromEnvironment(String[] args) {
        String machineName = resolveMachineName();
        String machineId = getEnv("CREDB_CRAWLER_MACHINE_ID", resolveMachineId(machineName));
        String scanRoots = getEnv("CREDB_CRAWLER_SCAN_ROOTS", "D:\\");
        int workerThreads = Integer.parseInt(getEnv("CREDB_CRAWLER_WORKER_THREADS", "4"));
        boolean verbose = false;
        Integer outputLimit = null;
        boolean includeHidden = false;
        String outputFile = null;

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
                case "--output", "-o" -> {
                    if (index + 1 >= args.length) {
                        throw new IllegalArgumentException("Missing value for " + arg);
                    }
                    outputFile = args[++index];
                }
                default -> {
                    if (normalized.startsWith("--root=") || normalized.startsWith("--start=")) {
                        scanRoots = arg.substring(arg.indexOf('=') + 1);
                    } else if (normalized.startsWith("--limit=")) {
                        outputLimit = Integer.parseInt(arg.substring(arg.indexOf('=') + 1));
                    } else if (normalized.startsWith("--output=")) {
                        outputFile = arg.substring(arg.indexOf('=') + 1);
                    } else if (normalized.equals("--include-hidden=true")) {
                        includeHidden = true;
                    } else {
                        throw new IllegalArgumentException("Unknown argument: " + arg);
                    }
                }
            }
        }

        return new CrawlerConfig(machineId, machineName, scanRoots, workerThreads, verbose, outputLimit, includeHidden, outputFile);
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

    private static String resolveMachineName() {
        String computerName = System.getenv("COMPUTERNAME");
        if (computerName != null && !computerName.isBlank()) {
            return computerName;
        }

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception exception) {
            return "unknown-machine";
        }
    }

    private static String resolveMachineId(String machineName) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();

                if (mac == null || mac.length == 0 || networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                return formatMacAddress(mac);
            }
        } catch (SocketException exception) {
            return machineName;
        }

        return machineName;
    }

    private static String formatMacAddress(byte[] mac) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < mac.length; index++) {
            builder.append(String.format("%02X", mac[index]));
            if (index < mac.length - 1) {
                builder.append("-");
            }
        }
        return builder.toString();
    }
}
