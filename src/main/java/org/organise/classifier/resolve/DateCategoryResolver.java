// Language: java
package org.organise.classifier.resolve;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DateCategoryResolver implements CategoryResolver {

    private final Map<String, List<LocalDateTime>> dateCategoryJson;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private final String UNKNOWN_CATEGORY_PLACEHOLDER;

    public DateCategoryResolver(Map<String, List<LocalDateTime>> dateCategoryJson) {
        this(dateCategoryJson, "Unknown");
    }

    public DateCategoryResolver(Map<String, List<LocalDateTime>> dateCategoryJson, String unknownCategoryPlaceHolder) {
        if (dateCategoryJson == null || dateCategoryJson.isEmpty()) {
            throw new IllegalArgumentException("Map of date categories cannot be empty.");
        }
        this.dateCategoryJson = dateCategoryJson;
        this.UNKNOWN_CATEGORY_PLACEHOLDER = unknownCategoryPlaceHolder;
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
        return UNKNOWN_CATEGORY_PLACEHOLDER;
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