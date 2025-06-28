package org.organise.classifier;

import org.organise.classifier.resolve.FileSizeCategoryResolver;
import org.organise.extractor.FileSizeExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ClassifyByFileSizeTest {

    @TempDir
    Path tempDir;

    // Stub implementation for FileSizeExtractor
    static class StubFileSizeExtractor extends FileSizeExtractor {
        private final long size;

        StubFileSizeExtractor(long size) {
            this.size = size;
        }

        @Override
        public Long extract(Path file) {
            return size;
        }
    }

    // Stub implementation for FileSizeCategoryResolver
    static class StubFileSizeCategoryResolver extends FileSizeCategoryResolver {
        private final String category;

        StubFileSizeCategoryResolver(String category) {
            super(java.util.Collections.singletonMap(category, Long.MAX_VALUE));
            this.category = category;
        }

        @Override
        public String lookup(String fileSize) {
            return category;
        }
    }

    @Test
    void classify_validFile_returnsCategory() {
        FileSizeExtractor extractor = new StubFileSizeExtractor(12345);
        FileSizeCategoryResolver resolver = new StubFileSizeCategoryResolver("SMALL");
        ClassifyByFileSize classifier = new ClassifyByFileSize(resolver, extractor);
        Path file = tempDir.resolve("sample.txt");

        String result = classifier.classify(file);

        assertEquals("SMALL", result);
    }

    @Test
    void classify_nullFile_throwsException() {
        FileSizeExtractor extractor = new StubFileSizeExtractor(12345);
        FileSizeCategoryResolver resolver = new StubFileSizeCategoryResolver("SMALL");
        ClassifyByFileSize classifier = new ClassifyByFileSize(resolver, extractor);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> classifier.classify(null));
        assertEquals("File path cannot be null", exception.getMessage());
    }
}