package org.organise.classifier;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class ClassifyByDate implements ClassifyFile<LocalDateTime> {

    @Override
    public LocalDateTime classify(Path file) {
        return null;
    }
}
