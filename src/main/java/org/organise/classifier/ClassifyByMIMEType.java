package org.organise.classifier;

import java.nio.file.Path;

public class ClassifyByMIMEType implements ClassifyFile<String> {
    @Override
    public String classify(Path file) {
        return "";
    }
}
