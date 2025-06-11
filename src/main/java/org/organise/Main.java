package org.organise;

import org.organise.classifier.ClassifyByFileExtension;
import org.organise.classifier.lookup.ExtensionLookUp;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.extractor.FileExtensionExtractor;
import org.organise.mover.FileMover;
import org.organise.organiser.FileOrganiser;
import org.organise.scanner.DirectoryScanner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path configurationPath = Path.of("/home/rega/Tutorial/config/Config.json");
        Path sourcePath = Path.of("/run/media/rega/Emperor/UNSTRUCTURED");

        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationPath);
        ExtensionLookUp extensionLookUp = new ExtensionLookUp(jsonConfigurationLoader.getJsonConfigurationObject());

        FileExtensionExtractor fileExtensionExtractor = new FileExtensionExtractor();

        ClassifyByFileExtension classifyByFileExtension = new ClassifyByFileExtension(
                extensionLookUp,
                fileExtensionExtractor
        );

        FileMover fileMover = new FileMover();

        DirectoryScanner directoryScanner = new DirectoryScanner(sourcePath);

        FileOrganiser fileOrganiser = new FileOrganiser(
                classifyByFileExtension,
                fileMover,
                directoryScanner
        );

        Path destinationDirectory = Paths.get("/run/media/rega/Emperor/Destination");

        fileOrganiser.organise(destinationDirectory);

    }
}