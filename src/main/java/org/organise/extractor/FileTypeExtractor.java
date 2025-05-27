package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTypeExtractor implements DataExtractor<String> {
    @Override
    public String extract(Path file) {
        try {
            return extractType(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract MIME type for: " + file, e);
        }
    }

    private String extractType(Path file) throws IOException {
        String fileType = Files.probeContentType(file);
        return fileType != null ? fileType : "Unknown Type";
    }
}
