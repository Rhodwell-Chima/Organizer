package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LastModifiedDateTimeExtractor extends DateTimeExtractor {

    @Override
    protected LocalDateTime getDateTime(Path file) throws IOException {
        return getLastModifiedDateTime(file);
    }

    private LocalDateTime getLastModifiedDateTime(Path file) throws IOException {
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file, BasicFileAttributes.class);
        return LocalDateTime.ofInstant(basicFileAttributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
    }
}
