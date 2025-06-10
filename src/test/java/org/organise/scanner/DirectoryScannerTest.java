package org.organise.scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryScannerTest {

    @Test
    void testConstructorWithNonDirectory(@TempDir Path tempDir) throws IOException {
        // Create a temporary file instead of directory.
        Path tempFile = Files.createTempFile(tempDir, "test", ".txt");
        Exception exception = assertThrows(RuntimeException.class, () -> new DirectoryScanner(tempFile));
        assertTrue(exception.getMessage().contains("is not a Directory. Please input a valid Directory."));
    }

    @Test
    void testScanEmptyDirectory(@TempDir Path tempDir) {
        // An empty directory should return an empty list.
        DirectoryScanner scanner = new DirectoryScanner(tempDir);
        List<Path> files = scanner.scan();
        assertNotNull(files);
        assertTrue(files.isEmpty());
    }

    @Test
    void testScanDirectoryWithFiles(@TempDir Path tempDir) throws IOException {
        // Create two temporary files.
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));

        DirectoryScanner scanner = new DirectoryScanner(tempDir);
        List<Path> files = scanner.scan();

        assertNotNull(files);
        assertEquals(2, files.size());
    }

    @Test
    void testScanWithDepth(@TempDir Path tempDir) throws IOException {
        // Create a nested directory structure.
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        // Create a file in the sub-directory.
        Files.createFile(subDir.resolve("fileSub.txt"));
        // Create a file in the root tempDir.
        Files.createFile(tempDir.resolve("fileRoot.txt"));

        DirectoryScanner scanner = new DirectoryScanner(tempDir);

        // Valid depth of 2 to include the sub-directory file.
        List<Path> filesWithDepth = scanner.scan(2);
        // There should be two files.
        assertEquals(2, filesWithDepth.size());

        // Requesting a depth more than available will throw an IllegalArgumentException.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> scanner.scan(3));
        assertTrue(exception.getMessage().contains("depth provided is greater than the possible depth"));
    }
}