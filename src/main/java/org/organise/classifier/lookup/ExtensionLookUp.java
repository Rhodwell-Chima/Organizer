package org.organise.classifier.lookup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.organise.configuration.ConfigurationLoader;

import java.nio.file.Path;

public class ExtensionLookUp {
    private final JsonObject extensionCategories;

    public ExtensionLookUp(Path configFilePath) {
        ConfigurationLoader configurationLoader = ConfigurationLoader.getInstance(configFilePath);
        this.extensionCategories = configurationLoader.getJsonConfigurationObject();
    }

    public String getExtensionCategory(String extension) {
        for (String category : extensionCategories.keySet()) {
            if (isExtensionInCategory(extension, category)) return category;
        }
        return null;
    }

    private boolean isExtensionInCategory(String extension, String group) {
        JsonArray extensionsArray = extensionCategories.getAsJsonArray(group);
        // Convert the extension to a JsonElement
        JsonPrimitive extensionElement = new JsonPrimitive(extension);
        return extensionsArray.contains(extensionElement);
    }
}
