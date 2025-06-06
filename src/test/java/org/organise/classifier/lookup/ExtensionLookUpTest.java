package org.organise.classifier.lookup;

import org.junit.jupiter.api.Test;
import org.organise.configuration.ConfigurationLoader;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionLookUpTest {

    @Test
    void getExtensionCategory() {
        Path configurationPath = Path.of("/home/rega/Tutorial/config/Config.json");
        ConfigurationLoader configurationLoader = ConfigurationLoader.getInstance(configurationPath);
        ExtensionLookUp extensionLookUp = new ExtensionLookUp(
                configurationLoader.getJsonConfigurationObject()
        );
        String actual = extensionLookUp.getExtensionCategory(".mp3");
        assertEquals("Audio", actual);
    }
}