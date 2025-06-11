package org.organise.mover;

import java.nio.file.Path;

public interface Mover {
    void move(Path sourceFile, Path destinationDirectory);
}
