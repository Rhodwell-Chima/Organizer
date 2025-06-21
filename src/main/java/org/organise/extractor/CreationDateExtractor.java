package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CreationDateExtractor extends DateTimeExtractor {

    @Override
    protected LocalDateTime getDateTime(Path file) throws IOException {
        return getCreationDate(file);
    }

    private LocalDateTime getCreationDate(Path file) throws IOException {
        BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        return LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault());
    }
}
