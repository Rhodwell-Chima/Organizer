package org.organise.classifier;

import java.nio.file.Path;

public interface ClassifyFile<T> {
    T classify(Path file);
}
