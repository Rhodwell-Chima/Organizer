package org.organise.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationLoaderTest {

    @Test
    void getJsonConfigurationArray() {
        Path path = Path.of("/home/rega/Tutorial/config/Config.json");
        ConfigurationLoader configurationLoader = ConfigurationLoader.getInstance(path);
        JsonObject configurationObject = configurationLoader.getJsonConfigurationObject();
        var n = configurationObject.get("Audio").getAsJsonArray();
        var a = n.toString();
        assertEquals("Audio", a);
    }
}