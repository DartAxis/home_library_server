package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Book getBookByIsbnOriginal(String isbn);
    Book getBookByIsbnTranslate(String isbn);

    Book getBookByTitleIgnoreCase(String title);
    Book getBookByOriginalTitleIgnoreCase(String title);
}
