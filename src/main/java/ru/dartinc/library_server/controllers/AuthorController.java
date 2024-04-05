package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.dto.AuthorDTO;
import ru.dartinc.library_server.services.AuthorService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/rest/api/webversion1.0/author")
public class AuthorController {
    private final AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        return new ResponseEntity<>(service.getAllAuthorsToFront(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getAutorsByFio(@RequestParam("surname") String surname,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("middlename") String middlename) {

        if(surname != null && !surname.isBlank() && !surname.isEmpty()){
            var result = service.getAllAuthorsToFrontBySurname(surname);
            if(name != null && !name.isBlank() && !name.isEmpty()){
                result = result.stream().filter( x-> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
            }
            if(middlename != null && !middlename.isBlank() && !middlename.isEmpty()){
                result = result.stream().filter( x-> x.getMiddlename().equalsIgnoreCase(middlename)).collect(Collectors.toList());
            }
            if(!result.isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        var map = new HashMap<String, String>();
        map.put("message", "Не найдено таких авторов, возможно не задана фамилия автора для поиска");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody AuthorDTO authorDTO){
        if(authorDTO != null &&
                authorDTO.getSurname()!=null &&
                !authorDTO.getSurname().isEmpty() &&
                !authorDTO.getSurname().isBlank()
        ){
            return new ResponseEntity<>(service.add(authorDTO),HttpStatus.OK);
        }

        var map = new HashMap<String, String>();
        map.put("message", "Возможно не задана фамилия автора для добавления");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}
