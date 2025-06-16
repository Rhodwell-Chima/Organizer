package org.organise.configuration.extractor;

import com.google.gson.JsonElement;

import java.util.Map;

public interface ConfigurationExtractor {
    Map<String, JsonElement> extract();
}
