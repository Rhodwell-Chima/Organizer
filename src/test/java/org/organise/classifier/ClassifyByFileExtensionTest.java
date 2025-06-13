package org.organise.classifier;

import org.junit.jupiter.api.Test;
import org.organise.classifier.resolve.ExtensionCategoryResolver;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.extractor.FileExtensionExtractor;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ClassifyByFileExtensionTest {

    @Test
    void classify() {
        Path filePath = Path.of("/home/rega/Downloads/carbon.png");
        Path configurationFilePath = Path.of("/home/rega/Tutorial/config/Config.json");
        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationFilePath);
        ClassifyByFileExtension classify = new ClassifyByFileExtension(
                new ExtensionCategoryResolver(jsonConfigurationLoader.getJsonConfigurationObject()),
                new FileExtensionExtractor()
        );
        String actual = classify.classify(filePath);
        assertEquals("images", actual);
    }
}