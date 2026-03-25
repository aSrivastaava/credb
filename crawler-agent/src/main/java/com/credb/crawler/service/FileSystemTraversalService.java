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
import java.util.List;

public final class FileSystemTraversalService {
    public CrawlSummary crawl(CrawlerConfig config) {
        List<FileRecord> records = new ArrayList<>();
        TraversalStats stats = new TraversalStats();

        for (Path root : config.resolvedScanRoots()) {
            if (!Files.exists(root)) {
                stats.inaccessiblePaths++;
                continue;
            }

            try {
                Files.walkFileTree(root, new CrawlVisitor(config.machineId(), config.includeHidden(), records, stats));
                stats.scannedRoots++;
            } catch (IOException exception) {
                stats.inaccessiblePaths++;
            }
        }

        return new CrawlSummary(
                stats.scannedRoots,
                stats.directories,
                stats.files,
                stats.skippedHiddenEntries,
                stats.hiddenEntries,
                stats.inaccessiblePaths,
                records
        );
    }

    private static final class CrawlVisitor extends SimpleFileVisitor<Path> {
        private final String machineId;
        private final boolean includeHidden;
        private final List<FileRecord> records;
        private final TraversalStats stats;

        private CrawlVisitor(String machineId, boolean includeHidden, List<FileRecord> records, TraversalStats stats) {
            this.machineId = machineId;
            this.includeHidden = includeHidden;
            this.records = records;
            this.stats = stats;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            boolean hidden = isHidden(dir);
            if (hidden) {
                stats.hiddenEntries++;
                if (!configIncludeHidden()) {
                    stats.skippedHiddenEntries++;
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }

            stats.directories++;
            records.add(toRecord(dir, attrs, true, "accessible", hidden));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            boolean hidden = isHidden(file);
            if (hidden) {
                stats.hiddenEntries++;
                if (!configIncludeHidden()) {
                    stats.skippedHiddenEntries++;
                    return FileVisitResult.CONTINUE;
                }
            }

            stats.files++;
            records.add(toRecord(file, attrs, false, "accessible", hidden));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exception) {
            stats.inaccessiblePaths++;
            records.add(new FileRecord(
                    machineId,
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
        private int directories;
        private int files;
        private int skippedHiddenEntries;
        private int hiddenEntries;
        private int inaccessiblePaths;
    }
}
