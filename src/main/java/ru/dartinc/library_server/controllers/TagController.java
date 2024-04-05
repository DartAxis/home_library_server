package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.TagDTO;
import ru.dartinc.library_server.services.TagService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/api/webversion1.0/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService service;

    @GetMapping
    public ResponseEntity<List<TagDTO>> getAll(){
        return new ResponseEntity<>(service.getAllTagsToFront(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addTag(@RequestBody TagDTO tagDTO) {

        if (tagDTO != null) {
            var result = service.add(tagDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String,String>();
        map.put("message","Не получено тело TagDTO");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> editTag(@RequestBody TagDTO tagDTO) {
        if (tagDTO != null) {
            var result = service.editTag(tagDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело TagDTO или не найдено поле ID");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
