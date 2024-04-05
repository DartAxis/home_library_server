package ru.dartinc.library_server.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileLibUtils {
    public static String fileToString(String path) {
        String result = "";
        try {
            result = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean stringToFile(String path, String body) {
        try {
            FileUtils.writeStringToFile(new File(path), body, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
