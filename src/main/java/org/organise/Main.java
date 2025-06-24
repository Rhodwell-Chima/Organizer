package org.organise;

import org.organise.classifier.ClassifyByDate;
import org.organise.classifier.resolve.DateCategoryResolver;
import org.organise.classifier.resolve.FileSizeCategoryResolver;
import org.organise.configuration.JSONConfigurationLoader;
import org.organise.configuration.extractor.ConfigurationExtractor;
import org.organise.configuration.extractor.DateConfigurationExtractor;
import org.organise.configuration.extractor.FileSizeConfigurationExtractor;
import org.organise.extractor.FileSizeExtractor;
import org.organise.extractor.LastModifiedDateTimeExtractor;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path configurationPath = Path.of("/home/rega/IdeaProjects/Organizer/Configuration.json");
        Path sourcePath = Path.of("/run/media/rega/Emperor/CTF");

        JSONConfigurationLoader jsonConfigurationLoader = JSONConfigurationLoader.getInstance(configurationPath);

        ConfigurationExtractor<List<LocalDateTime>> configurationExtractor = new DateConfigurationExtractor(jsonConfigurationLoader.getJsonConfigurationObject(), "Date_Rules");

        ClassifyByDate classifyByDate = new ClassifyByDate(
                new DateCategoryResolver(configurationExtractor.extract()),
                new LastModifiedDateTimeExtractor()
        );

        System.out.println(classifyByDate.classify(
                        Path.of("/home/rega/CTF/rev_flagcasino/casino")
                )
        );

//        FileMover fileMover = new FileMover();
//
//        DirectoryScanner directoryScanner = new DirectoryScanner(sourcePath);
//
//        FileOrganiser fileOrganiser = new FileOrganiser(
//                classifyByFileExtension,
//                fileMover,
//                directoryScanner
//        );
//
////        fileOrganiser.setDepth(4);
//
//        Path destinationDirectory = Paths.get("/run/media/rega/Emperor/OrganisedBySoftware");
//
//        fileOrganiser.organise(destinationDirectory);

        System.out.println(configurationExtractor.extract());
        //for (Map.Entry<String, List<String>> entry : configurationExtractor.extract().entrySet())

        FileSizeConfigurationExtractor fileSizeConfigurationExtractor = new FileSizeConfigurationExtractor(
                jsonConfigurationLoader.getJsonConfigurationObject(),
                "Size_Rules"
        );


        FileSizeExtractor fileSizeExtractor = new FileSizeExtractor();
        System.out.println("The size of the Config in bytes = " + fileSizeExtractor.extract(configurationPath));

        System.out.println(fileSizeConfigurationExtractor.convertStringToLong("100KB"));
        System.out.println();
        System.out.println(fileSizeConfigurationExtractor.extract());

        FileSizeCategoryResolver fileSizeCategoryResolver = new FileSizeCategoryResolver(fileSizeConfigurationExtractor.extract());
        System.out.println(fileSizeCategoryResolver.lookup("1024"));
    }
}