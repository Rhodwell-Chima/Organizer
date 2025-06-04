package org.organise.classifier.lookup;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionLookUpTest {

    @Test
    void getExtensionCategory() {
        ExtensionLookUp extensionLookUp = new ExtensionLookUp(Path.of("/home/rega/Tutorial/config/Config.json"));
        String actual = extensionLookUp.getExtensionCategory(".mp3");
        assertEquals("Audio", actual);
    }
}