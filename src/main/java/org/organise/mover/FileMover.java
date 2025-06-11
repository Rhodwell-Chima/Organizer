package org.organise.mover;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileMover implements Mover {
    @Override
    public void move(Path sourceFile, Path destinationDirectory) {
        if (Files.isDirectory(destinationDirectory)) {
            Path destinationFilePath = destinationDirectory.resolve(sourceFile.getFileName());
            relocateFile(sourceFile, destinationFilePath);
        } else {
            throw new IllegalArgumentException("Destination Directory must be a Directory");
        }
    }

    private void relocateFile(Path sourceFilePath, Path destinationFilePath) {
        if (Files.isRegularFile(sourceFilePath))
            try {
                Files.move(sourceFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        else
            throw new IllegalArgumentException("Source File must be a regular file.");
    }
}
