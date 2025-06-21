// Language: java
package org.organise.classifier.resolve;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class DateCategoryResolver implements CategoryResolver {

    private final Map<String, List<LocalDateTime>> dateCategoryJson;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public DateCategoryResolver(Map<String, List<LocalDateTime>> dateCategoryJson) {
        if (dateCategoryJson == null || dateCategoryJson.isEmpty()) {
            throw new IllegalArgumentException("Map of date categories cannot be empty.");
        }
        this.dateCategoryJson = dateCategoryJson;
    }

    @Override
    public String lookup(String input) {
        return resolveCategoryForDate(input);
    }

    private String resolveCategoryForDate(String date) {
        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        LocalDateTime dateTime = convertStringToDateTime(date);
        for (Map.Entry<String, List<LocalDateTime>> entry : dateCategoryJson.entrySet()) {
            if (isDateInCategory(dateTime, entry.getValue())) {
                return entry.getKey();
            }
        }
        return "Unknown";
    }

    private LocalDateTime convertStringToDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, FORMATTER);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateTime, e);
        }
    }

    private boolean isDateInCategory(LocalDateTime date, List<LocalDateTime> thresholds) {
        for (LocalDateTime threshold : thresholds) {
            if (date.isAfter(threshold) || date.isEqual(threshold)) {
                return true;
            }
        }
        return false;
    }
}