package ru.dartinc.library_server.utils;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZMethod;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SevenZCompress {

    private SevenZCompress() {}

    public static boolean compress(String name, File... files) {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(name))) {//name-выходной зипФайл
            out.setContentCompression(SevenZMethod.LZMA2); // выбрали метод компрессии
            for (File file : files) {
                addToArchiveCompression(out, file);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static void addToArchiveCompression(SevenZOutputFile out, File file) throws IOException {
        String name = file.getName();
        if (file.isFile()) {
            SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
            out.putArchiveEntry(entry);

            FileInputStream in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int count = 0;
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
            out.closeArchiveEntry();
            in.close();
        }
    }

    public static boolean copyFile7zOutBookTemp(File source, File destination) {
        File srcFile = new File(String.valueOf(source));
        File destFile = new File(String.valueOf(destination));
        try {
            FileUtils.copyFile(srcFile, destFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void cleanBookTemp(File tempDir) { // имя директории, которую нужно очистить
        for (File file : tempDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }
}