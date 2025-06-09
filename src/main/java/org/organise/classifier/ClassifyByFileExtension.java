package org.organise.classifier;

import org.organise.classifier.lookup.ExtensionLookUp;
import org.organise.extractor.FileExtensionExtractor;

import java.nio.file.Path;

public class ClassifyByFileExtension implements ClassifyFile<String> {

    private final ExtensionLookUp extensionLookUp;
    private final FileExtensionExtractor fileExtensionExtractor;

    public ClassifyByFileExtension(
            ExtensionLookUp extensionLookUp,
            FileExtensionExtractor fileExtensionExtractor
    ) {
        if (extensionLookUp == null || fileExtensionExtractor == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.extensionLookUp = extensionLookUp;
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
        return extensionLookUp.getExtensionCategory(extractedFileExtension);
    }
}