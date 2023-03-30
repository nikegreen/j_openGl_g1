package ru.nikegreen.openGlGame1.util;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
    @Getter
    @Setter
    private static boolean isJar = true;
    public static String separatorNormalizer(String fileName) {
        if (File.separatorChar == '/' || (isJar == true)) {
            fileName = fileName.replace('\\', File.separatorChar );
        } else {
            fileName = fileName.replace('/', File.separatorChar );
        }
        return fileName;
    }
}
