package org.organise.classifier.resolve;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ExtensionCategoryResolver implements CategoryResolver {
    private final JsonObject extensionCategories;

    public ExtensionCategoryResolver(JsonObject extensionCategories) {
        if (extensionCategories == null) {
            throw new IllegalArgumentException("Extension categories cannot be null");
        }
        this.extensionCategories = extensionCategories;
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
        return "Unknown";
    }

    private boolean isExtensionInCategory(String extension, String group) {
        JsonArray extensionsArray = extensionCategories.getAsJsonArray(group);
        if (extensionsArray == null) {
            return false;
        }
        JsonPrimitive extensionElement = new JsonPrimitive(extension);
        return extensionsArray.contains(extensionElement);
    }
}