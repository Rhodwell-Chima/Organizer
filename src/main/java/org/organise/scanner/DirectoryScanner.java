package org.organise.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DirectoryScanner implements Scanner<List<Path>> {
    private final Path sourceDirectory;

    public DirectoryScanner(Path sourceDirectory) {
        if (!Files.isDirectory(sourceDirectory))
            throw new RuntimeException(sourceDirectory + " is not a Directory. Please input a valid Directory.");
        this.sourceDirectory = sourceDirectory;
    }

    @Override
    public List<Path> scan() {
        return retrieveFilePaths();
    }

    @Override
    public List<Path> scan(int depth) {
        if (depth > 0)
            return retrieveFilePaths(depth);
        else
            throw new IllegalArgumentException("Depth must be greater than Zero.");
    }

    private List<Path> retrieveFilePaths() {

        try (Stream<Path> streamOfFiles = Files.list(this.sourceDirectory)) {
            return streamOfFiles
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Path> retrieveFilePaths(int depth) {
        if (calculateMaxDepth() >= (depth - 1))
            try (Stream<Path> streamOfFiles = Files.walk(this.sourceDirectory, depth)) {
                return streamOfFiles
                        .filter(Files::isRegularFile)
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        else
            throw new IllegalArgumentException("The depth provided is greater than the possible depth.");
    }

    private int calculateMaxDepth() {
        int sourceDirectoryNameCount = this.sourceDirectory.getNameCount();

        try (Stream<Path> pathStream = Files.walk(this.sourceDirectory)) {
            return pathStream
                    .filter(Files::isDirectory)
                    .mapToInt(path -> path.getNameCount() - sourceDirectoryNameCount)
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
