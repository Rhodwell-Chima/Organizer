package org.organise.extractor;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileExtensionExtractorTest {

    @Test
    void simpleExtensionExtractSingleExtension() {
        FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();
        String extractedExtension = fileExtensionExtractor.extract(Path.of("document.pdf"));
        assertEquals("pdf", extractedExtension);
    }

    @Test
    void simpleExtensionExtractMultiExtension() {
        FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();
        String extractedExtension = fileExtensionExtractor.extract(Path.of("document.tar.gz"));
        assertEquals("tar.gz", extractedExtension);
    }

    @Test
    void simpleExtensionExtractNoExtension() {
        FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();
        String extractedExtension = fileExtensionExtractor.extract(Path.of("document"));
        assertEquals("", extractedExtension);
    }

    @Test
    void simpleExtensionExtractEndsWithDot() {
        FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();
        String extractedExtension = fileExtensionExtractor.extract(Path.of("document."));
        assertEquals("", extractedExtension);
    }

}