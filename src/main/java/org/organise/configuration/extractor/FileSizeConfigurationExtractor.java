package org.organise.configuration.extractor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSizeConfigurationExtractor implements ConfigurationExtractor<Long> {
    private final JsonObject jsonObject;
    private final String OBJECT_KEY;
    private static final Pattern SIZE_PATTERN = Pattern.compile("([0-9]*\\.?[0-9]+)\\s*(kb|mb|gb)?", Pattern.CASE_INSENSITIVE);

    public FileSizeConfigurationExtractor(JsonObject jsonObject, String objectKey) {
        if (jsonObject == null) {
            throw new IllegalArgumentException("JSON object cannot be null");
        }
        this.jsonObject = jsonObject;
        this.OBJECT_KEY = objectKey;
    }

    @Override
    public Map<String, Long> extract() {
        return parseSizeRules(getJsonObject().asMap());
    }

    private Map<String, Long> parseSizeRules(Map<String, JsonElement> jsonMap) {
        Map<String, Long> sizeMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            String size = entry.getValue().getAsString();
            if (size == null || size.isEmpty())
                throw new IllegalStateException("The value of size cannot be null or empty");
            sizeMap.put(entry.getKey(), convertStringToLong(size));
        }
        return sizeMap;
    }

    private JsonObject getJsonObject() {
        if (!jsonObject.has(OBJECT_KEY))
            throw new IllegalStateException("Key " + OBJECT_KEY + " not found in JSON object");
        JsonElement jsonElement = jsonObject.get(OBJECT_KEY);
        if (jsonElement == null || !jsonElement.isJsonObject())
            throw new IllegalStateException("Value for key " + OBJECT_KEY + " is not a JSON object");
        return jsonObject.getAsJsonObject(OBJECT_KEY);
    }

    public Long convertStringToLong(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }
        Matcher matcher = SIZE_PATTERN.matcher(input.trim());
        if (!matcher.matches()) {
            throw new RuntimeException("The given string cannot be parsed.");
        }

        double number = Double.parseDouble(matcher.group(1));
        String unit = matcher.group(2) == null ? "" : matcher.group(2).toLowerCase();

        return switch (unit) {
            case "" -> (long) number;
            case "kb" -> (long) (number * 1024);
            case "mb" -> (long) (number * 1024 * 1024);
            case "gb" -> (long) (number * 1024 * 1024 * 1024);
            default -> throw new RuntimeException("No conversion available for unit: " + unit);
        };
    }
}