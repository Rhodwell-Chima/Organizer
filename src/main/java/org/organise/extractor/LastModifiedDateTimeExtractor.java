package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LastModifiedDateTimeExtractor implements DataExtractor<LocalDateTime> {
    @Override
    public LocalDateTime extract(Path file) {
        this.validateFile(file);
        try {
            return getLastModifiedDateTime(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getLastModifiedDateTime(Path file) throws IOException {
        BasicFileAttributes basicFileAttributes = Files.readAttributes(file, BasicFileAttributes.class);
        return LocalDateTime.ofInstant(basicFileAttributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
    }

    private void validateFile(Path file) {
        if (file == null || file.getFileName() == null) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        if (!Files.isReadable(file)) {
            throw new IllegalArgumentException("File is not readable: " + file);
        }
    }
}
