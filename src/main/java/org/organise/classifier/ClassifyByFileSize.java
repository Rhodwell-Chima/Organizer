package org.organise.classifier;

import org.organise.classifier.resolve.FileSizeCategoryResolver;
import org.organise.extractor.FileSizeExtractor;

import java.nio.file.Path;

public class ClassifyByFileSize implements ClassifyFile<String> {
    private final FileSizeCategoryResolver fileSizeCategoryResolver;
    private final FileSizeExtractor fileSizeExtractor;

    public ClassifyByFileSize(FileSizeCategoryResolver fileSizeCategoryResolver, FileSizeExtractor fileSizeExtractor) {
        if (fileSizeCategoryResolver == null || fileSizeExtractor == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.fileSizeCategoryResolver = fileSizeCategoryResolver;
        this.fileSizeExtractor = fileSizeExtractor;
    }

    @Override
    public String classify(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        return classifyFileBySize(file);
    }

    private String classifyFileBySize(Path file) {
        long extractedFileSize = fileSizeExtractor.extract(file);
        return fileSizeCategoryResolver.lookup(Long.toString(extractedFileSize));
    }
}
