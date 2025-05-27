package org.organise.classifier;

import java.nio.file.Path;

public interface ClassifyFile {
    String classify(Path file);
}
