package ru.dartinc.library_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Author;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;
    private String surname;
    private String name;
    private String middlename;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.surname = author.getSurname();
        this.name = author.getName();
        this.middlename = author.getMiddlename();
    }
}
