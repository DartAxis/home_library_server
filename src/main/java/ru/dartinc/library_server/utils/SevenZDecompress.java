package ru.dartinc.library_server.utils;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SevenZDecompress {
    // in-путь к исходному файлу, destinationFilePath-путь к файлу назначения
    public static void decompress(String in, String destinationFilePath) throws IOException {
        SevenZFile sevenZFile = new SevenZFile(new File(in));//SevenZFile-считываем файл 7z
        SevenZArchiveEntry entry; //запись entry в архиве 7z
        //пока есть следующий файл
        while ((entry = sevenZFile.getNextEntry()) != null) {//sevenZFile.getNextEntry() -возвращает следующую запись архива
            //getDefaultName()-Наследует имя файла по умолчанию от имени архива - если известно.
            if (entry.getName().contains(sevenZFile.getDefaultName())) {
                File curfile = new File(destinationFilePath, entry.getName());
                FileOutputStream out = new FileOutputStream(curfile);
                byte[] content = new byte[(int) entry.getSize()];
                sevenZFile.read(content, 0, content.length);
                out.write(content);
                out.close();
            }
        }
    }
}