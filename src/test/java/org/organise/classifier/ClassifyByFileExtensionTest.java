package org.organise.classifier;

import org.junit.jupiter.api.Test;
import org.organise.classifier.resolve.ExtensionCategoryResolver;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.configuration.extractor.ConfigurationExtractor;
import org.organise.configuration.extractor.ExtensionConfigurationExtractor;
import org.organise.extractor.FileExtensionExtractor;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassifyByFileExtensionTest {

    @Test
    void classify() {
        Path filePath = Path.of("/home/rega/Downloads/carbon.png");
        Path configurationFilePath = Path.of("/home/rega/Tutorial/config/Configuration.json");
        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationFilePath);
        ConfigurationExtractor<List<String>> configurationExtractor = new ExtensionConfigurationExtractor(jsonConfigurationLoader.getJsonConfigurationObject(), "Extension_Rules");
        ClassifyByFileExtension classify = new ClassifyByFileExtension(
                new ExtensionCategoryResolver(configurationExtractor.extract()),
                new FileExtensionExtractor()
        );
        String actual = classify.classify(filePath);
        assertEquals("images", actual);
    }
}