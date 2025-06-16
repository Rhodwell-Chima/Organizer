package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MIMETypeExtractor implements DataExtractor<String> {
    private static final String UNKNOWN_TYPE = "Unknown Type";

    @Override
    public String extract(Path file) {
        validateFile(file);
        try {
            return extractType(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract MIME type for file: " + file, e);
        }
    }

    private String extractType(Path file) throws IOException {
        String fileType = Files.probeContentType(file);
        return fileType != null ? fileType : UNKNOWN_TYPE;
    }

    private void validateFile(Path file) {
        if (file == null || file.getFileName() == null) {
            throw new IllegalArgumentException("File path cannot be null or empty.");
        }
    }
}