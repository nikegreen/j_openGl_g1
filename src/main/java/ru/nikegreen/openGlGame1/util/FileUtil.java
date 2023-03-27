package ru.nikegreen.openGlGame1.util;

import org.lwjgl.BufferUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
