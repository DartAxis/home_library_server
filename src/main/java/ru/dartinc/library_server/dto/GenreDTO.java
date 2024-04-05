package ru.dartinc.library_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    private Long id;
    private String genreTitle;

    public GenreDTO(Genre genre) {
        this.id = genre.getId();
        this.genreTitle = genre.getGenreTitle();
    }
}
