package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CreationDateExtractor implements DataExtractor<LocalDateTime> {
    @Override
    public LocalDateTime extract(Path file) {
        try {
            return getCreationDate(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDateTime getCreationDate(Path file) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        return LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault());
    }
}
