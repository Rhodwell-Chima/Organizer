package org.organise.configuration.extractor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

public class ExtensionConfigurationExtractor implements ConfigurationExtractor {
    private final JsonObject jsonObject;
    private final String OBJECT_KEY = "Extension_Rules";

    public ExtensionConfigurationExtractor(JsonObject jsonObject) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        this.jsonObject = jsonObject;
    }

    @Override
    public Map<String, JsonElement> extract() {
        return this.getJsonObject().asMap();
    }

    private JsonObject getJsonObject() {
        if (!jsonObject.has(OBJECT_KEY))
            throw new IllegalStateException("Key " + OBJECT_KEY + " not found in JSON object");
        JsonElement jsonElement = jsonObject.get(OBJECT_KEY);
        if (jsonElement == null || !jsonElement.isJsonObject())
            throw new IllegalStateException("Value for key " + OBJECT_KEY + " is not a JSON object");
        return jsonObject.getAsJsonObject(OBJECT_KEY);
    }
}
