package ru.dartinc.library_server.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.BookInDTO;
import ru.dartinc.library_server.dto.BookOutDTO;
import ru.dartinc.library_server.services.BookService;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/rest/api/webversion1.0/book")
public class BookController {

    private final BookService service;


    // метод для скачивания самой распакованной книги
    @GetMapping("/{id}/file")
    public void getBookFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        log.info("Запрос на скачивание файла книги : {}", id);
        var path = service.getBookFile(id);
        if (downloadFile(path, response)) {
            log.info("Удачное скачивание файла книги : {}", id);
        } else {
            log.error("Неудачное скачивание файла книги : {}", id);
        }
        service.clearTempDir();
    }

    // сделать метод для скачивания архива книги
    @GetMapping("/{id}/archive")
    public void getArchive(@PathVariable Long id, HttpServletResponse response) throws IOException {
        log.info("Запрос на скачивание архива книги : {}", id);
        var path = service.getArchiveFile(id);
        downloadFile(path, response);
    }

    // метод для скачивания картинки книги
    @GetMapping("/{id}/image")
    public void getPicture(@PathVariable Long id, HttpServletResponse response) throws IOException {
        log.info("Запрос на скачивание обложки книги : {}", id);
        var path = service.getPicture(id);
        downloadFile(path, response);
    }

    @GetMapping(value = "/{id}/pic", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable Long id) throws IOException {
        var path = service.getPicture(id);
        if (Files.exists(Paths.get(path))) {
            var in = new FileInputStream(path);
            return IOUtils.toByteArray(in);
        }
        return new byte[0];
    }

    @GetMapping("/{id}/info")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getInfoById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getBookInfoById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id) {
        log.info("Запрос сущности книги : {}", id);
        var book = service.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(new BookOutDTO(book), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> addBookWithFiles(@RequestBody BookInDTO bookDTO) {
        log.info("Добавление книги");
        var serviceResult = service.addBook(bookDTO);
        var result = serviceResult + "\n" + bookDTO.getOriginalTitle();
        if (serviceResult.equals("Книга добавлена :")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result = "Ошибка!!!\n" + result;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    public boolean downloadFile(String path, HttpServletResponse response) throws IOException {
        if (path != null) {
            Path file = Paths.get(path);
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            response.setContentType(contentType);
            response.setContentLengthLong(Files.size(file));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                    .filename(file.getFileName().toString(), StandardCharsets.UTF_8)
                    .build()
                    .toString());
            Files.copy(file, response.getOutputStream());
            return true;
        }
        return false;
    }
}
