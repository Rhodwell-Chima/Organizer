package org.organise.classifier.resolve;

import org.junit.jupiter.api.Test;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.configuration.extractor.ConfigurationExtractor;
import org.organise.configuration.extractor.ExtensionConfigurationExtractor;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionCategoryResolverTest {

    @Test
    void getExtensionCategory() {
        Path configurationPath = Path.of("/home/rega/Tutorial/config/Configuration.json");
        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationPath);
        ConfigurationExtractor<List<String>> configurationExtractor = new ExtensionConfigurationExtractor(jsonConfigurationLoader.getJsonConfigurationObject(), "Extension_Rules");
        ExtensionCategoryResolver extensionCategoryResolver = new ExtensionCategoryResolver(
                configurationExtractor.extract()
        );
        String actual = extensionCategoryResolver.lookup("mp3");
        assertEquals("Audio", actual);
    }
}