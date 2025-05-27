package org.organise.extractor;

import java.nio.file.Path;

public interface DataExtractor<T>{
    T extract(Path file);
}
