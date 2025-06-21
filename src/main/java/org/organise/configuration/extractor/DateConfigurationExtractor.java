package org.organise.configuration.extractor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateConfigurationExtractor implements ConfigurationExtractor<LocalDateTime> {
    private final JsonObject jsonObject;
    private final String OBJECT_KEY;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public DateConfigurationExtractor(JsonObject jsonObject, String objectKey) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        this.jsonObject = jsonObject;
        this.OBJECT_KEY = objectKey;
    }

    @Override
    public Map<String, List<LocalDateTime>> extract() {
        return parseDateRules(getJsonObject().asMap());
    }


    private Map<String, List<LocalDateTime>> parseDateRules(Map<String, JsonElement> jsonMap) {
        Map<String, List<LocalDateTime>> dateRules = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            JsonArray jsonArray = entry.getValue().getAsJsonArray();
            List<LocalDateTime> dates = new ArrayList<>();
            if (jsonArray != null) {
                for (JsonElement element : jsonArray) {
                    dates.add(LocalDateTime.parse(element.getAsString(), FORMATTER));
                }
            }
            // Sorting in descending order allows efficient threshold comparisons if needed.
            dates.sort(Collections.reverseOrder());
            dateRules.put(entry.getKey(), dates);
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
