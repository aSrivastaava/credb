package com.credb.crawler.service;

import com.credb.crawler.model.CrawlSummary;
import com.credb.crawler.model.FileRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public final class CrawlExportService {
    public void exportToJson(Path outputPath, CrawlSummary summary) throws IOException {
        Path parent = outputPath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.writeString(outputPath, toJson(summary));
    }

    private String toJson(CrawlSummary summary) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        appendNumber(builder, "scannedRoots", summary.scannedRoots(), true);
        appendNumber(builder, "visibleDirectories", summary.visibleDirectories(), true);
        appendNumber(builder, "visibleFiles", summary.visibleFiles(), true);
        appendNumber(builder, "hiddenDirectories", summary.hiddenDirectories(), true);
        appendNumber(builder, "hiddenFiles", summary.hiddenFiles(), true);
        appendNumber(builder, "skippedHiddenEntries", summary.skippedHiddenEntries(), true);
        appendNumber(builder, "inaccessiblePaths", summary.inaccessiblePaths(), true);
        builder.append("  \"records\": [\n");

        for (int index = 0; index < summary.records().size(); index++) {
            FileRecord record = summary.records().get(index);
            builder.append("    {\n");
            appendString(builder, "machineId", record.machineId(), true, 6);
            appendString(builder, "machineName", record.machineName(), true, 6);
            appendString(builder, "fullPath", record.fullPath(), true, 6);
            appendString(builder, "fileName", record.fileName(), true, 6);
            appendBoolean(builder, "directory", record.directory(), true, 6);
            appendBoolean(builder, "hidden", record.hidden(), true, 6);
            appendNumber(builder, "sizeBytes", record.sizeBytes(), true, 6);
            appendNullableInstant(builder, "lastModifiedAt", record.lastModifiedAt(), true, 6);
            appendString(builder, "accessStatus", record.accessStatus(), false, 6);
            builder.append("    }");
            if (index < summary.records().size() - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }

        builder.append("  ]\n");
        builder.append("}\n");
        return builder.toString();
    }

    private void appendString(StringBuilder builder, String key, String value, boolean trailingComma, int indent) {
        builder.append(" ".repeat(indent))
                .append("\"").append(key).append("\": ")
                .append("\"").append(escape(value)).append("\"");
        if (trailingComma) {
            builder.append(",");
        }
        builder.append("\n");
    }

    private void appendNullableInstant(StringBuilder builder, String key, Instant value, boolean trailingComma, int indent) {
        builder.append(" ".repeat(indent))
                .append("\"").append(key).append("\": ");
        if (value == null) {
            builder.append("null");
        } else {
            builder.append("\"").append(value).append("\"");
        }
        if (trailingComma) {
            builder.append(",");
        }
        builder.append("\n");
    }

    private void appendBoolean(StringBuilder builder, String key, boolean value, boolean trailingComma, int indent) {
        builder.append(" ".repeat(indent))
                .append("\"").append(key).append("\": ")
                .append(value);
        if (trailingComma) {
            builder.append(",");
        }
        builder.append("\n");
    }

    private void appendNumber(StringBuilder builder, String key, long value, boolean trailingComma) {
        appendNumber(builder, key, value, trailingComma, 2);
    }

    private void appendNumber(StringBuilder builder, String key, long value, boolean trailingComma, int indent) {
        builder.append(" ".repeat(indent))
                .append("\"").append(key).append("\": ")
                .append(value);
        if (trailingComma) {
            builder.append(",");
        }
        builder.append("\n");
    }

    private String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
