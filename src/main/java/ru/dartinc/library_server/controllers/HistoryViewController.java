package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dartinc.library_server.dto.BookOutDTO;
import ru.dartinc.library_server.services.HistoryViewService;

import java.util.List;

@RestController
@RequestMapping("rest/api/webversion1.0/history")
@RequiredArgsConstructor
public class HistoryViewController {
    private final HistoryViewService service;

    @GetMapping
    public ResponseEntity<Object> getAllHistoryForCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(username, HttpStatus.OK);
    }
}
