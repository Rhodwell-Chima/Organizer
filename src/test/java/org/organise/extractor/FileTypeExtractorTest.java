package org.organise.extractor;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileTypeExtractorTest {

    @Test
    void testExtract_ValidFile() {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("/home/rega/IdeaProjects/Organizer/src/main/java/org/organise/Main.java");
        String fileType = extractor.extract(file);
        assertEquals("text/plain", fileType); // Assuming "example.txt" resolves to "text/plain"
    }

    @Test
    void testExtract_UnknownType() {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("/home/rega/Tutorial/Java/FileOrganizer/Unknown.unknown");
        String fileType = extractor.extract(file);
        assertEquals("Unknown Type", fileType);
    }

    @Test
    void testExtract_InvalidFile() {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("");
        String fileType = extractor.extract(file);
        assertEquals("Unknown Type", fileType);
    }
}