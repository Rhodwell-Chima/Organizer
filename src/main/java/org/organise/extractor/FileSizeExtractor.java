package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSizeExtractor implements DataExtractor<Integer> {

    @Override
    public Integer extract(Path file) {
        this.validateFile(file);
        try {
            return this.getFileSizeInBytes(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer getFileSizeInBytes(Path file) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        return Math.toIntExact(attributes.size());
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
