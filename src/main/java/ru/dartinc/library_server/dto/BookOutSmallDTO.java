package ru.dartinc.library_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Book;

@Getter
@Setter
@NoArgsConstructor
public class BookOutSmallDTO {
    private Long id;
    private String uuid;
    private String title;

    public BookOutSmallDTO(Book book){
        this.id = book.getId();
        this.uuid = book.getUuid().toString();
        this.title = book.getTitle();
    }
}
