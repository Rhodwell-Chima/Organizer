package org.organise;

import org.organise.classifier.*;
import org.organise.classifier.resolve.DateCategoryResolver;
import org.organise.classifier.resolve.ExtensionCategoryResolver;
import org.organise.classifier.resolve.FileSizeCategoryResolver;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.configuration.extractor.DateConfigurationExtractor;
import org.organise.configuration.extractor.ExtensionConfigurationExtractor;
import org.organise.configuration.extractor.FileSizeConfigurationExtractor;
import org.organise.extractor.CreationDateExtractor;
import org.organise.extractor.FileExtensionExtractor;
import org.organise.extractor.FileSizeExtractor;
import org.organise.extractor.LastModifiedDateTimeExtractor;
import org.organise.mover.FileMover;
import org.organise.organiser.FileOrganiser;
import org.organise.scanner.DirectoryScanner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(
            Paths.get("/home/rega/IdeaProjects/Organizer/Configuration.json"));
    private static final DateConfigurationExtractor dateConfigurationExtractor = new DateConfigurationExtractor(
            jsonConfigurationLoader.getJsonConfigurationObject(),
            "Date_Rules"
    );
    private static final ExtensionConfigurationExtractor extensionConfigurationExtractor = new ExtensionConfigurationExtractor(
            jsonConfigurationLoader.getJsonConfigurationObject(),
            "Extension_Rules"
    );
    private static final FileSizeConfigurationExtractor fileSizeConfigurationExtractor = new FileSizeConfigurationExtractor(
            jsonConfigurationLoader.getJsonConfigurationObject(),
            "Size_Rules"
    );

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter full Source Directory path to scan for files to move");
        String sourceDirectoryPathString = input.nextLine();
        Path sourceDirectoryPath = Path.of(sourceDirectoryPathString);

        System.out.println("Enter a Destination Directory path to move files to");
        String destinationDirectoryPathString = input.nextLine();
        Path destinationDirectoryPath = Path.of(destinationDirectoryPathString);

        DirectoryScanner directoryScanner = new DirectoryScanner(sourceDirectoryPath);
        int sourceDirectoryDepth = directoryScanner.getDepth();

        System.out.println("Source Directory Depth: " + sourceDirectoryDepth);
        System.out.println("Enter the depth for which files must be scanned. The depth must be less than or equal to the source directory depth (" + sourceDirectoryDepth + ")");

        int depthToScan = input.nextInt();
        if (depthToScan > sourceDirectoryDepth || depthToScan < 0) {
            depthToScan = sourceDirectoryDepth;
        }
        System.out.println("Select the classification method to use.");

        System.out.println("1. File Extension");
        System.out.println("2. MIME Type");
        System.out.println("3. Creation Date");
        System.out.println("4. Last Modified Date");
        System.out.println("5. File Size");
        int classificationMethod = input.nextInt();
        ClassifyFile<String> classifyFile = selectClassificationMethod(classificationMethod);

        FileOrganiser fileOrganiser = new FileOrganiser(
                classifyFile,
                new FileMover(),
                directoryScanner
        );
        fileOrganiser.setDepth(depthToScan);
        fileOrganiser.organise(destinationDirectoryPath);
        input.close();
    }

    private static ClassifyFile<String> selectClassificationMethod(int classificationMethod) {
        return switch (classificationMethod) {
            case 1 -> new ClassifyByFileExtension(
                    new ExtensionCategoryResolver(extensionConfigurationExtractor.extract()),
                    new FileExtensionExtractor()
            );
            case 2 -> new ClassifyByMIMEType();
            case 3 -> new ClassifyByDate(
                    new DateCategoryResolver(dateConfigurationExtractor.extract()),
                    new CreationDateExtractor()
            );
            case 4 -> new ClassifyByDate(
                    new DateCategoryResolver(dateConfigurationExtractor.extract()),
                    new LastModifiedDateTimeExtractor()
            );
            case 5 -> new ClassifyByFileSize(
                    new FileSizeCategoryResolver(fileSizeConfigurationExtractor.extract()),
                    new FileSizeExtractor()
            );
            default -> null;
        };
    }
}