package org.organise.extractor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;

import static java.nio.file.Files.probeContentType;

public class FileExtensionExtractor implements DataExtractor<String> {
    @Override
    public String extract(Path file) {
        return null;
    }

    public String simpleExtensionExtract(Path file) throws IOException {
        String contentType = probeContentType(file);
        return contentType != null ? contentType : "No extension found";
    }
}
