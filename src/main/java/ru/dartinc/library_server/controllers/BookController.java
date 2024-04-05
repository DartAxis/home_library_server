package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.BookInDTO;
import ru.dartinc.library_server.dto.BookOutDTO;
import ru.dartinc.library_server.services.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public void getBookFile(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        var path = service.getBookFile(id);
        downloadFile(path,request,response);
        service.clearTempDir();
    }
    // сделать метод для скачивания архива книги
    @GetMapping("/{id}/archive")
    public void getArchive(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        var path = service.getArchiveFile(id);
        downloadFile(path,request,response);
    }

    // метод для скачивания картинки книги
    @GetMapping("/{id}/pic")
    public void getPicture(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException{
        var path = service.getPicture(id);
        downloadFile(path,request,response);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<Object> getInfoById(@PathVariable Long id){
        return new ResponseEntity<>(service.getBookInfoById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Long id){
        var book = service.getBookById(id);
        if(book!=null){
            return new ResponseEntity<>(new BookOutDTO(book),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Object> addBookWithFiles(@RequestBody BookInDTO bookDTO){
        log.info("Добавление книги");
        var serviceResult = service.addBook(bookDTO);
        var result = serviceResult +"\n" + bookDTO.getOriginalTitle();
        if(serviceResult.equals("Книга добавлена :")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result = "Ошибка!!!\n" + result;
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    public boolean downloadFile(String path, HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(path!=null) {
            Path file = Paths.get(path);
            // Get the media type of the file
            String contentType = Files.probeContentType(file);
            if (contentType == null) {
                // Use the default media type
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            response.setContentType(contentType);
            // File Size
            response.setContentLengthLong(Files.size(file));

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                    .filename(file.getFileName().toString(), StandardCharsets.UTF_8)
                    .build()
                    .toString());
            // Response data to the client
            Files.copy(file, response.getOutputStream());
            return true;
        }
        return false;
    }
}
