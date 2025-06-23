package org.organise.configuration.extractor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class ExtensionConfigurationExtractor implements ConfigurationExtractor<List<String>> {
    private final JsonObject jsonObject;
    private final String OBJECT_KEY;

    public ExtensionConfigurationExtractor(JsonObject jsonObject, String objectKey) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        this.jsonObject = jsonObject;
        this.OBJECT_KEY = objectKey;
    }

    @Override
    public Map<String, List<String>> extract() {
        return parseExtensionRules(getJsonObject().asMap());
    }


    private Map<String, List<String>> parseExtensionRules(Map<String, JsonElement> jsonMap) {
        Map<String, List<String>> extensionRules = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            List<String> extensions = new ArrayList<>();
            if (jsonArray != null) {
                for (JsonElement element : jsonArray) {
                    extensions.add(element.getAsString());
                }
            }
            extensionRules.put(entry.getKey(), extensions);
        }
        return extensionRules;
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
