package org.organise.classifier;

import org.organise.classifier.resolve.DateCategoryResolver;
import org.organise.extractor.DateTimeExtractor;

import java.nio.file.Path;

public class ClassifyByDate implements ClassifyFile<String> {

    private final DateCategoryResolver dateCategoryResolver;
    private final DateTimeExtractor dateTimeExtractor;

    public ClassifyByDate(
            DateCategoryResolver dateCategoryResolver,
            DateTimeExtractor dateTimeExtractor
    ) {
        if (dateCategoryResolver == null || dateTimeExtractor == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.dateCategoryResolver = dateCategoryResolver;
        this.dateTimeExtractor = dateTimeExtractor;
    }

    @Override
    public String classify(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        return categorizeFileByDateTime(file);
    }

    private String categorizeFileByDateTime(Path file) {
        String extractedDateTime = dateTimeExtractor.extract(file).toString();
        return dateCategoryResolver.lookup(extractedDateTime);
    }
}
