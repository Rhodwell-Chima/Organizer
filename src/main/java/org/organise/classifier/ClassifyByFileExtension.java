package org.organise.classifier;

import org.organise.classifier.resolve.ExtensionCategoryResolver;
import org.organise.extractor.FileExtensionExtractor;

import java.nio.file.Path;

public class ClassifyByFileExtension implements ClassifyFile<String> {

    private final ExtensionCategoryResolver extensionCategoryResolver;
    private final FileExtensionExtractor fileExtensionExtractor;

    public ClassifyByFileExtension(
            ExtensionCategoryResolver extensionCategoryResolver,
            FileExtensionExtractor fileExtensionExtractor
    ) {
        if (extensionCategoryResolver == null || fileExtensionExtractor == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.extensionCategoryResolver = extensionCategoryResolver;
        this.fileExtensionExtractor = fileExtensionExtractor;
    }

    @Override
    public String classify(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        return categorizeFileByExtension(file);
    }

    private String categorizeFileByExtension(Path file) {
        String extractedFileExtension = "." + fileExtensionExtractor.extract(file);
        return extensionCategoryResolver.lookup(extractedFileExtension);
    }
}