package com.imgarena.tennis_matches.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Resources {

    private Resources() {}

    public static String readFile(String filePath) {
        try {
            Path path = Paths.get(Resources.class.getClassLoader().getResource(filePath).toURI());
            byte[] fileBytes = Files.readAllBytes(path);
            return new String(fileBytes);
        } catch (Exception unexpected) {
            throw new RuntimeException("Unexpected error reading file: " + filePath, unexpected);
        }
    }
}
