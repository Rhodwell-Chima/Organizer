package org.organise.configuration.extractor;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

public interface ConfigurationExtractor<T> {
    Map<String, List<T>> extract();
}
