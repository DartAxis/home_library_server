package ru.dartinc.library_server.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/webversion1.0/info")
public class InfoServerController {
    @Value("${version}")
    private String version;

    @GetMapping
    public ResponseEntity<String> getInfoServer(){
        var builder = new StringBuilder();
        builder.append(" Library server v.")
                .append(version);
        return new ResponseEntity<>(builder.toString(),HttpStatus.OK);
    }
}
