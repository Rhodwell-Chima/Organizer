package org.organise.configuration;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JSONConfigurationLoaderTest {

    @Test
    void getJsonConfigurationArray() {
        Path path = Path.of("/home/rega/Tutorial/config/Config.json");
        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(path);
        JsonObject configurationObject = jsonConfigurationLoader.getJsonConfigurationObject();
        var n = configurationObject.get("Audio").getAsJsonArray();
        var a = n.toString();
        assertEquals("Audio", a);
    }
}