package org.organise.configuration.extractor;

import java.util.Map;

public interface ConfigurationExtractor<T> {
    Map<String, T> extract();
}
