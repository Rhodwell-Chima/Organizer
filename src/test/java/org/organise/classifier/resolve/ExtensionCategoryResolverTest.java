package org.organise.classifier.resolve;

import org.junit.jupiter.api.Test;
import org.organise.configuration.JSONConfigurationLoader;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionCategoryResolverTest {

    @Test
    void getExtensionCategory() {
        Path configurationPath = Path.of("/home/rega/Tutorial/config/Config.json");
        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationPath);
        ExtensionCategoryResolver extensionCategoryResolver = new ExtensionCategoryResolver(
                jsonConfigurationLoader.getJsonConfigurationObject()
        );
        String actual = extensionCategoryResolver.lookup(".mp3");
        assertEquals("Audio", actual);
    }
}