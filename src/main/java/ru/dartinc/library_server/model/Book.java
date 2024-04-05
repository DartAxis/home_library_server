package ru.dartinc.library_server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Название книги
    @Column(name = "title")
    private String title;
    //UUID
    @Column(name = "uuid")
    private UUID uuid;
    //Оригинальное название
    @Column(name = "originaltitle")
    private String originalTitle;
    //Год издания
    @Column(name = "yearofedition")
    private String yearOfEdition;
    //ISBN оригинала
    @Column(name = "isbnoriginal")
    private String isbnOriginal;
    //ISBN перевода
    @Column(name = "isbntranslate")
    private String isbnTranslate;
    //Год издания перевода
    @Column(name = "yearofeditiontranslate")
    private String yearOfEditionTranslate;
    //Издательство
    @OneToOne
    @JoinColumn(name = "pubhouse_id")
    private PubHouse pubHouse;
    //Издательство перевода
    @OneToOne
    @JoinColumn(name = "pubhousetranslate_id")
    private PubHouse pubHouseTranslate;
    //путь до архива с книгой
    @Column(name = "pathtozipbook", unique = true)
    private String pathToZipBook;
    //формат книги
    @Column(name = "format")
    private String fileFormatBook;
    //Жанр
    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    // Авторы
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "authors_id"))
    private Set<Author> authors;
    //Таги
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_tags", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToOne
    @JoinColumn(name = "seriaid")
    private Seria seria;

    @Column(name = "pathtopic")
    private String pathToPic;
}
