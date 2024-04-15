package ru.dartinc.library_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class BookFindRequestDTO {
    private Map<String,String> filters;
}
