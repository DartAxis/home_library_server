package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.GenreDTO;
import ru.dartinc.library_server.dto.TagDTO;
import ru.dartinc.library_server.services.GenreService;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rest/api/webversion1.0/genre")
public class GenreController {
    private final GenreService service;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return new ResponseEntity<>(service.getAllGenresToFront(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addGenre(@RequestBody GenreDTO genreDto) {
        if (genreDto != null) {
            var result = service.add(genreDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело GenreDTO");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> editGenre(@RequestBody GenreDTO genreDto) {
        if (genreDto != null) {
            var result = service.editGenre(genreDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело GenreDTO или не найдено поле ID");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
