package org.organise.configuration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ConfigurationLoader {
    private static volatile ConfigurationLoader instance;
    private JsonObject jsonConfigurationObject;
    private final Path configFilePath;

    private ConfigurationLoader(Path configFilePath) {
        this.configFilePath = configFilePath;
        loadConfig();
    }

    public static ConfigurationLoader getInstance(Path configFilePath) {
        if (instance == null) {
            synchronized (ConfigurationLoader.class) {
                if (instance == null) {
                    instance = new ConfigurationLoader(configFilePath);
                }
            }
        }
        return instance;
    }

    private void loadConfig() {
        try {
            String content = Files.readString(configFilePath);
            jsonConfigurationObject = JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from: " + configFilePath, e);
        }
    }

    public void reload() {
        loadConfig();
    }

    public JsonObject getJsonConfigurationObject() {
        return jsonConfigurationObject;
    }
}