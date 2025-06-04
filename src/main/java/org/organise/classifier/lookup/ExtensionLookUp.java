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
        for (String group : extensionCategories.keySet()) {
            JsonArray extensionsArray = extensionCategories.getAsJsonArray(group);
            // Convert the extension to a JsonElement
            JsonPrimitive extensionElement = new JsonPrimitive(extension);
            if (extensionsArray.contains(extensionElement)) {
                return group;
            }
        }
        return null;
    }
}
