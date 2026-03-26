package com.credb.crawler.model;

import java.time.Instant;

public record FileRecord(
        String machineId,
        String fullPath,
        String fileName,
        boolean directory,
        boolean hidden,
        long sizeBytes,
        Instant lastModifiedAt,
        String accessStatus
) {
}
