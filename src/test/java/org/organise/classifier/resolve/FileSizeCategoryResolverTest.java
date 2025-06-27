package org.organise.classifier.resolve;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class FileSizeCategoryResolverTest {

    private FileSizeCategoryResolver createResolverWithDefaultMap() {
        Map<String, Long> map = new LinkedHashMap<>();
        // Categories defined in order: first match wins
        map.put("Small", 1024L);          // file sizes less than 1024 bytes
        map.put("Medium", 1048576L);        // file sizes less than 1048576 bytes
        map.put("Large", Long.MAX_VALUE);   // everything else
        return new FileSizeCategoryResolver(map, "UnknownSize");
    }

    @Test
    void testValidConversion() {
        FileSizeCategoryResolver resolver = createResolverWithDefaultMap();
        // "500" converts to 500 bytes, which is less than "Small" threshold (1024)
        String categorySmall = resolver.lookup("500");
        assertEquals("Small", categorySmall);

        String categoryMedium = resolver.lookup("5000");
        assertEquals("Medium", categoryMedium);

        String categoryLarge = resolver.lookup("1048577");
        assertEquals("Large", categoryLarge);
    }

    @Test
    void testInvalidStringThrowsException() {
        FileSizeCategoryResolver resolver = createResolverWithDefaultMap();
        Exception exception = assertThrows(RuntimeException.class, () -> resolver.lookup("invalid_size"));
        assertTrue(exception.getMessage().contains("Failed to parse size:"));
    }

    @Test
    void testNullInputThrowsException() {
        FileSizeCategoryResolver resolver = createResolverWithDefaultMap();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> resolver.lookup(null));
        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    void testEmptyInputThrowsException() {
        FileSizeCategoryResolver resolver = createResolverWithDefaultMap();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> resolver.lookup(""));
        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    void testEmptyMapInitializationThrowsException() {
        Map<String, Long> emptyMap = new LinkedHashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new FileSizeCategoryResolver(emptyMap));
        assertTrue(exception.getMessage().contains("Map of size categories cannot be empty"));
    }
}