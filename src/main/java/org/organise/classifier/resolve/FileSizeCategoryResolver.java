package org.organise.classifier.resolve;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class FileSizeCategoryResolver implements CategoryResolver {
    private final Map<String, Long> sizeCategoryMap;
    private final String UNKNOWN_CATEGORY_PLACEHOLDER;

    public FileSizeCategoryResolver(Map<String, Long> sizeCategoryMap) {
        this(sizeCategoryMap, "UnknownSize");
    }

    public FileSizeCategoryResolver(Map<String, Long> sizeCategoryMap, String unknownCategoryPlaceholder) {
        if (sizeCategoryMap == null || sizeCategoryMap.isEmpty()) {
            throw new IllegalArgumentException("Map of size categories cannot be empty.");
        }
        this.sizeCategoryMap = sizeCategoryMap;
        UNKNOWN_CATEGORY_PLACEHOLDER = unknownCategoryPlaceholder;
    }

    @Override
    public String lookup(String input) {
        return resolveCategoryForSize(input);
    }

    private String resolveCategoryForSize(String fileSizeString) {
        if (fileSizeString == null || fileSizeString.isEmpty()) {
            throw new IllegalArgumentException("File Size cannot be null or empty");
        }
        long fileSizeAsLong = convertStringToLong(fileSizeString);
        for (Map.Entry<String, Long> entry : sizeCategoryMap.entrySet()) {
            if (isSizeInCategory(fileSizeAsLong, entry.getValue())) {
                return entry.getKey();
            }
        }
        return UNKNOWN_CATEGORY_PLACEHOLDER;
    }

    private long convertStringToLong(String fileSizeString) {
        try {
            return Long.parseLong(fileSizeString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse size: " + fileSizeString, e);
        }
    }

    private boolean isSizeInCategory(Long fileSize, Long thresholds) {
        return fileSize.compareTo(thresholds) <= 0;
    }
}
