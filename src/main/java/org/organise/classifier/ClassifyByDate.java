package org.organise.classifier;

import java.nio.file.Path;

public class ClassifyByDate implements ClassifyFile<String> {

    @Override
    public String classify(Path file) {
        if (file == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        return null;
    }
}
