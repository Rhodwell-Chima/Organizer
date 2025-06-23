package org.organise.classifier.resolve;

import java.util.List;
import java.util.Map;

public class ExtensionCategoryResolver implements CategoryResolver {
    private final Map<String, List<String>> extensionCategories;
    private final String UNKNOWN_CATEGORY_PLACEHOLDER;

    public ExtensionCategoryResolver(Map<String, List<String>> extensionCategories) {
        this(extensionCategories, "Unknown");
    }

    public ExtensionCategoryResolver(Map<String, List<String>> extensionCategories, String unknownCategoryPlaceholder) {
        if (extensionCategories == null) {
            throw new IllegalArgumentException("Extension categories cannot be null");
        }
        this.extensionCategories = extensionCategories;
        this.UNKNOWN_CATEGORY_PLACEHOLDER = unknownCategoryPlaceholder;
    }

    @Override
    public String lookup(String input) {
        return getExtensionCategory(input);
    }

    public String getExtensionCategory(String extension) {
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("Extension cannot be null or empty");
        }

        for (String category : extensionCategories.keySet()) {
            try {
                if (isExtensionInCategory(extension, category)) {
                    return category;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return UNKNOWN_CATEGORY_PLACEHOLDER;
    }

    private boolean isExtensionInCategory(String extension, String group) {
        List<String> extensionsList = extensionCategories.get(group);
        return extensionsList != null && extensionsList.contains(extension);
    }
}