package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.PubHouseDTO;
import ru.dartinc.library_server.services.PubHouseService;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rest/api/webversion1.0/pubhouse")
public class PubHouseController {
    private final PubHouseService service;

    @GetMapping
    public ResponseEntity<List<PubHouseDTO>> getAll(){
        return new ResponseEntity<>(service.getAllPubHousesToFront(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> addPubHouse(@RequestBody PubHouseDTO pubHouseDTO) {
        if (pubHouseDTO != null) {
            var result = service.add(pubHouseDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String,String>();
        map.put("message","Не получено тело PubHouseDTO");
        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<Object> editGenre(@RequestBody PubHouseDTO pubHouseDTO) {
        if (pubHouseDTO != null) {
            var result = service.editPubHouse(pubHouseDTO);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не получено тело PubHouseDTO или не найдено поле ID");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
