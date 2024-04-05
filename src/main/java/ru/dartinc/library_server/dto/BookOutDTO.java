package ru.dartinc.library_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Book;
import ru.dartinc.library_server.model.Tag;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class BookOutDTO {
    private Long id;
    private UUID uuid;
    private String title;
    private String seria;
    private String originalTitle;
    private String yearEdition;
    private String yearOfEditionTranslate;
    private String isbnOriginal;
    private String isbnTranslate;
    private String fileFormatBook;//
    private String genre;
    private String pubHouse;
    private String pubHouseTranslate;
    private Set<String> tags;
    private Set<String> authors;//

    public BookOutDTO(Book book) {
        this.id = book.getId();
        this.uuid = book.getUuid();
        this.title = book.getTitle();
        this.originalTitle = book.getOriginalTitle();
        this.yearEdition = book.getYearOfEdition();
        this.yearOfEditionTranslate = book.getYearOfEditionTranslate();
        this.isbnOriginal = book.getIsbnOriginal();
        this.isbnTranslate = book.getIsbnTranslate();
        this.fileFormatBook = book.getFileFormatBook();
        this.genre = book.getGenre().getGenreTitle();
        if(book.getPubHouse()!=null) {
            this.pubHouse = book.getPubHouse().getPubHouseName();
        }
        if(book.getPubHouseTranslate()!=null) {
            this.pubHouseTranslate = book.getPubHouseTranslate().getPubHouseName();
        }
        this.tags = book.getTags()
                .stream()
                .map(Tag::getTagTitle).collect(Collectors.toSet());
        this.authors = book.getAuthors()
                .stream()
                .map(a->a.getSurname() + (a.getName()!=null?" "+a.getName():"")+ (a.getMiddlename()!=null?" "+a.getMiddlename():""))
                .collect(Collectors.toSet());
        if(book.getSeria()!=null) {
            this.seria = book.getSeria().getSeriaTitle();
        }
    }
}