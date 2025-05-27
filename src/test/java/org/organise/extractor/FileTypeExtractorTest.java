package org.organise.extractor;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileTypeExtractorTest {

    @Test
    void testExtract_ValidFile() throws Exception {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("/home/rega/IdeaProjects/Organizer/src/main/java/org/organise/Main.java");
        String fileType = extractor.extract(file);
        assertEquals("text/plain", fileType); // Assuming "example.txt" resolves to "text/plain"
    }

    @Test
    void testExtract_UnknownType() throws Exception {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("/home/rega/Tutorial/Java/FileOrganizer/Unknown.unknown");
        String fileType = extractor.extract(file);
        assertEquals("Unknown Type", fileType);
    }

    @Test
    void testExtract_InvalidFile() {
        FileTypeExtractor extractor = new FileTypeExtractor();
        Path file = Path.of("/home/rega/Tutorial/nonexistent.file");
        assertThrows(RuntimeException.class, () -> extractor.extract(file));
    }
}