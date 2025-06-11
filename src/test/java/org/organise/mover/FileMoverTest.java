// File: src/test/java/org/organise/mover/FileMoverTest.java
package org.organise.mover;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileMoverTest {

    @TempDir
    Path tempDir;

    @Test
    void testValidMove() throws IOException {
        // Arrange: Create a temporary source file with content & a destination directory.
        Path sourceDir = Files.createDirectory(tempDir.resolve("sourceDir"));
        Path sourceFile = Files.createTempFile(sourceDir, "source", ".txt");
        String content = "Sample content";
        Files.write(sourceFile, content.getBytes());
        Path destinationDirectory = Files.createDirectory(tempDir.resolve("destDir"));

        // Act: Move the file.
        FileMover mover = new FileMover();
        mover.move(sourceFile, destinationDirectory);

        // In a valid move, the file should now be under the destination directory with the same name.
        Path movedFile = destinationDirectory.resolve(sourceFile.getFileName());

        // Assertions
        assertFalse(Files.exists(sourceFile), "Source file should no longer exist");
        assertTrue(Files.exists(movedFile), "File should exist in the destination directory");
        assertEquals(content, Files.readString(movedFile), "Content should remain unchanged");
    }

    @Test
    void testInvalidSource() throws IOException {
        // Arrange: Use a directory as source instead of a regular file.
        Path sourceDirectory = Files.createDirectory(tempDir.resolve("sourceDir"));
        Path destinationDirectory = Files.createDirectory(tempDir.resolve("destDir"));
        FileMover mover = new FileMover();

        // Act & Assert: Expect an IllegalArgumentException.
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            mover.move(sourceDirectory, destinationDirectory);
        });
        assertTrue(ex.getMessage().contains("Source File must be a regular file"),
                "Exception should mention invalid source file");
    }

    @Test
    void testInvalidDestination() throws IOException {
        // Arrange: Create a valid source file and use a file as destination.
        Path sourceDir = Files.createDirectory(tempDir.resolve("sourceDir"));
        Path sourceFile = Files.createTempFile(sourceDir, "source", ".txt");
        Files.write(sourceFile, "Content".getBytes());

        // Instead of a directory, destinationFile is created as a regular file.
        Path destinationFile = Files.createTempFile(tempDir, "notADir", ".txt");
        FileMover mover = new FileMover();

        // Act & Assert: Expect an IllegalArgumentException.
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            mover.move(sourceFile, destinationFile);
        });
        assertTrue(ex.getMessage().contains("Destination Directory must be a Directory"),
                "Exception should mention invalid destination directory");
    }
}