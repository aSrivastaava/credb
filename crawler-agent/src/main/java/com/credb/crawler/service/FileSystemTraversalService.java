package com.credb.crawler.service;

import com.credb.crawler.config.CrawlerConfig;
import com.credb.crawler.model.CrawlSummary;
import com.credb.crawler.model.FileRecord;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FileSystemTraversalService {
    public CrawlSummary crawl(CrawlerConfig config) {
        List<FileRecord> records = new ArrayList<>();
        TraversalStats stats = new TraversalStats();
        Set<Path> configuredRoots = new HashSet<>();

        for (Path root : config.resolvedScanRoots()) {
            if (!Files.exists(root)) {
                stats.inaccessiblePaths++;
                continue;
            }

            try {
                Path normalizedRoot = root.toAbsolutePath().normalize();
                configuredRoots.add(normalizedRoot);
                Files.walkFileTree(
                        normalizedRoot,
                        new CrawlVisitor(
                                config.machineId(),
                                config.machineName(),
                                config.includeHidden(),
                                configuredRoots,
                                records,
                                stats
                        )
                );
                stats.scannedRoots++;
            } catch (IOException exception) {
                stats.inaccessiblePaths++;
            }
        }

        return new CrawlSummary(
                stats.scannedRoots,
                stats.visibleDirectories,
                stats.visibleFiles,
                stats.hiddenDirectories,
                stats.hiddenFiles,
                stats.skippedHiddenEntries,
                stats.inaccessiblePaths,
                records
        );
    }

    private static final class CrawlVisitor extends SimpleFileVisitor<Path> {
        private final String machineId;
        private final String machineName;
        private final boolean includeHidden;
        private final Set<Path> configuredRoots;
        private final List<FileRecord> records;
        private final TraversalStats stats;

        private CrawlVisitor(
                String machineId,
                String machineName,
                boolean includeHidden,
                Set<Path> configuredRoots,
                List<FileRecord> records,
                TraversalStats stats
        ) {
            this.machineId = machineId;
            this.machineName = machineName;
            this.includeHidden = includeHidden;
            this.configuredRoots = configuredRoots;
            this.records = records;
            this.stats = stats;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            boolean configuredRoot = isConfiguredRoot(dir);
            boolean hidden = isHidden(dir);
            if (!configuredRoot) {
                if (hidden) {
                    stats.hiddenDirectories++;
                    if (!configIncludeHidden()) {
                        stats.skippedHiddenEntries++;
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                } else {
                    stats.visibleDirectories++;
                }
            }

            records.add(toRecord(dir, attrs, true, "accessible", hidden));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            boolean hidden = isHidden(file);
            if (hidden) {
                stats.hiddenFiles++;
                if (!configIncludeHidden()) {
                    stats.skippedHiddenEntries++;
                    return FileVisitResult.CONTINUE;
                }
            } else {
                stats.visibleFiles++;
            }
            records.add(toRecord(file, attrs, false, "accessible", hidden));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exception) {
            stats.inaccessiblePaths++;
            records.add(new FileRecord(
                    machineId,
                    machineName,
                    file.toAbsolutePath().toString(),
                    file.getFileName() == null ? file.toString() : file.getFileName().toString(),
                    false,
                    false,
                    0L,
                    null,
                    "inaccessible"
            ));
            return FileVisitResult.CONTINUE;
        }

        private FileRecord toRecord(Path path, BasicFileAttributes attrs, boolean directory, String accessStatus, boolean hidden) {
            return new FileRecord(
                    machineId,
                    machineName,
                    path.toAbsolutePath().toString(),
                    path.getFileName() == null ? path.toString() : path.getFileName().toString(),
                    directory,
                    hidden,
                    directory ? 0L : attrs.size(),
                    attrs.lastModifiedTime().toInstant(),
                    accessStatus
            );
        }

        private boolean configIncludeHidden() {
            return includeHidden;
        }

        private boolean isConfiguredRoot(Path path) {
            return configuredRoots.contains(path.toAbsolutePath().normalize());
        }

        private boolean isHidden(Path path) {
            try {
                return Files.isHidden(path);
            } catch (IOException exception) {
                return false;
            }
        }
    }

    private static final class TraversalStats {
        private int scannedRoots;
        private int visibleDirectories;
        private int visibleFiles;
        private int hiddenDirectories;
        private int hiddenFiles;
        private int skippedHiddenEntries;
        private int inaccessiblePaths;
    }
}
