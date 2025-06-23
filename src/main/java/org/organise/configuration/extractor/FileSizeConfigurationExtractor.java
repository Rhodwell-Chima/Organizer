package org.organise.configuration.extractor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class FileSizeConfigurationExtractor implements ConfigurationExtractor<Long> {
    private final JsonObject jsonObject;
    private final String OBJECT_KEY;

    public FileSizeConfigurationExtractor(JsonObject jsonObject, String objectKey) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        this.jsonObject = jsonObject;
        this.OBJECT_KEY = objectKey;
    }

    @Override
    public Map<String, Long> extract() {
        return Map.of();
    }

    private Map<String, Long> parseSizeRules(Map<String, JsonElement> jsonMap) {
        Map<String, Long> dateRules = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            String date = entry.getValue().getAsString();
            if (date == null || date.isEmpty())
                throw new IllegalStateException("The value of date cannot be null or empty");

        }
        return dateRules;
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
