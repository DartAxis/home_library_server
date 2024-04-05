package ru.dartinc.library_server.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Utils {

    public static String fileToBase64(String fileName) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean base64ToFile(String encoded, String fileName) {
        Path path = Paths.get(fileName);
        byte[] bytes = Base64.getDecoder().decode(encoded.getBytes());
        try {
            Files.write(path, bytes);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}