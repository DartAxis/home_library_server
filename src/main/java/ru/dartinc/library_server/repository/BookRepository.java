package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book getBookByIsbnOriginal(String isbn);
    Book getBookByIsbnTranslate(String isbn);

    Book getBookByTitleIgnoreCase(String title);
    Book getBookByOriginalTitleIgnoreCase(String title);

    @Query(value = """
            SELECT distinct(book.*) from books book
            join authors_books ab on ab.book_id = book.id
            join authors author on author.id = ab.authors_id
            join books_tags bt on book.id = bt.book_id
            join tags tag on tag.id = bt.tag_id
            join pubhouses pubhouse on pubhouse.id = book.pubhouse_id\s
            join pubhouses pubhousetr on pubhousetr.id =book.pubhousetranslate_id\s
            join genres genre on genre.id =book.genre_id
            join serias seria on seria.id =book.seriaid
            where ( :find is null or author.surname ilike :find)
            or ( :find is null or tag.tagtitle ilike :find)
            or ( :find is null or genre.genretitle ilike :find)
            or ( :find is null or pubhouse.pubhousename ilike :find)
            or ( :find is null or pubhousetr.pubhousename ilike :find)
            or ( :find is null or seria.seriatitle ilike :find)
            or ( :find is null or book.originaltitle ilike :find)
            or ( :find is null or book.title ilike :find)
            """

            ,nativeQuery = true)
    List<Book> findBookReuest(String find);
}
