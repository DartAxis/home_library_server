package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.PubHouseDTO;
import ru.dartinc.library_server.dto.SeriaDTO;
import ru.dartinc.library_server.services.SeriaService;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rest/api/webversion1.0/seria")
public class SeriaController {
    private final SeriaService service;

    @GetMapping
    public ResponseEntity<List<SeriaDTO>> getAll() {
        return new ResponseEntity<>(service.getAllSeriesToFront(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addSeria(@RequestBody SeriaDTO seriaDTO) {
        if (seriaDTO != null) {
            var result = service.add(seriaDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело SeriaDTO");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> editSeria(@RequestBody SeriaDTO seriaDTO) {
        if (seriaDTO != null) {
            var result = service.editSeria(seriaDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело SeriaDTO или не найдено поле ID");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
