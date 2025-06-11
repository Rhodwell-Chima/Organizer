package org.organise.organiser;

import org.organise.classifier.ClassifyFile;
import org.organise.mover.Mover;
import org.organise.scanner.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileOrganiser {
    private final ClassifyFile<String> classifyFile;
    private final Mover mover;
    private final Scanner<List<Path>> scanner;


    public FileOrganiser(ClassifyFile<String> classifyFile, Mover mover, Scanner<List<Path>> scanner) {
        this.classifyFile = classifyFile;
        this.mover = mover;
        this.scanner = scanner;
    }

    public void organise(Path destinationDirectory) {
        List<Path> pathList = getPathList();

        for (Path path : pathList) {
            String category = classifyFile.classify(path);
            Path categorisedDirectory = destinationDirectory.resolve(category);
            this.moveFile(path, categorisedDirectory);
        }
    }

    private List<Path> getPathList() {
        return scanner.scan();
    }

    private void moveFile(Path path, Path categorisedDirectory) {
        if (Files.exists(categorisedDirectory)) {
            mover.move(path, categorisedDirectory);
        } else {
            try {
                Files.createDirectory(categorisedDirectory);
                mover.move(path, categorisedDirectory);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
