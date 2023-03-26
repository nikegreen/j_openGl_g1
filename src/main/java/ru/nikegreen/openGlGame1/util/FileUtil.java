package ru.nikegreen.openGlGame1.util;

import java.io.File;

public class FileUtil {
    public static String separatorNormalizer(String fileName) {
        if (File.separatorChar == '/') {
            fileName = fileName.replace('\\', File.separatorChar );
        } else {
            fileName = fileName.replace('/', File.separatorChar );
        }
        return fileName;
    }
}
