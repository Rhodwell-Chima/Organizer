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
        return parseSizeRules(getJsonObject().asMap());
    }

    private Map<String, Long> parseSizeRules(Map<String, JsonElement> jsonMap) {
        Map<String, Long> stringLongHashMap = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonMap.entrySet()) {
            String size = entry.getValue().getAsString();
            if (size == null || size.isEmpty())
                throw new IllegalStateException("The value of size cannot be null or empty");
            stringLongHashMap.put(entry.getKey(), convertStringToLong(size));
        }
        return stringLongHashMap;
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
        long fileSizeInBytes;
        if (isNumeric(input)) {
            fileSizeInBytes = (long) (Double.parseDouble(input));
        } else if (input.toLowerCase().endsWith("kb")) {
            String newString = input.replaceAll("(?i)kb", "");
            try {
                fileSizeInBytes = kilobyteToByte(Double.parseDouble(newString));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        } else if (input.toLowerCase().endsWith("mb")) {
            String newString = input.replaceAll("(?i)mb", "");
            try {
                fileSizeInBytes = megabyteToByte(Double.parseDouble(newString));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        } else if (input.toLowerCase().endsWith("gb")) {
            String newString = input.replaceAll("(?i)gb", "");
            try {
                fileSizeInBytes = gigabyteToByte(Double.parseDouble(newString));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("The given string cannot be parsed.");
        }
        return fileSizeInBytes;
    }


    private Long gigabyteToByte(double gigabytes) {
        return (long) (gigabytes * 1024 * 1024 * 1024);
    }

    private Long megabyteToByte(double megabytes) {
        return (long) (megabytes * 1024 * 1024);
    }

    private Long kilobyteToByte(double megabytes) {
        return (long) (megabytes * 1024);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
