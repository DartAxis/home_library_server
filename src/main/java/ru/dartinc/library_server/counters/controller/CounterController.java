package ru.dartinc.library_server.counters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dartinc.library_server.counters.model.Counter;
import ru.dartinc.library_server.counters.service.CounterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counter")
public class CounterController {
    private final CounterService service;

    @GetMapping("/{name}")
    public ResponseEntity<Counter> getByName(@PathVariable String name){
        return new ResponseEntity<>(service.getByName(name), HttpStatus.OK);
    }


    @PostMapping("/{name}")
    public ResponseEntity<Counter> add(@PathVariable String name){
        return new ResponseEntity<>(service.add(name), HttpStatus.OK);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Counter> increaseCounter(@PathVariable String name){
        return new ResponseEntity<>(service.incCounter(name), HttpStatus.OK);
    }
}
