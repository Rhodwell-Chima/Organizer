package org.organise.configuration.extractor;

import java.util.List;
import java.util.Map;

public interface ConfigurationExtractor<T> {
    Map<String, List<T>> extract();
}
