package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public abstract class DateTimeExtractor implements DataExtractor<LocalDateTime> {
    @Override
    public LocalDateTime extract(Path file) {
        this.validateFile(file);
        try {
            return getDateTime(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract LocalDateTime getDateTime(Path file) throws IOException;

    protected void validateFile(Path file) {
        if (file == null || file.getFileName() == null) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
        if (!Files.isReadable(file)) {
            throw new IllegalArgumentException("File is not readable: " + file);
        }
    }
}
