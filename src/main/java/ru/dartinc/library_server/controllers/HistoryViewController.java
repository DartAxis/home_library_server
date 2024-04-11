package ru.dartinc.library_server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dartinc.library_server.dto.HistoryViewDTO;
import ru.dartinc.library_server.security.model.User;
import ru.dartinc.library_server.services.HistoryViewService;

import java.util.List;

@RestController
@RequestMapping("rest/api/webversion1.0/history")
@RequiredArgsConstructor
public class HistoryViewController {
    private final HistoryViewService service;

    @GetMapping
    public ResponseEntity<List<HistoryViewDTO>> getAllHistoryForCurrentUser(){
        var user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var historyList = service.getByUser(user).stream().map(HistoryViewDTO::new).toList();

        return new ResponseEntity<>(historyList, HttpStatus.OK);
    }
}
