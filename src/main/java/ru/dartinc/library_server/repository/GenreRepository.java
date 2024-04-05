package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre getGenreByGenreTitleIgnoreCase(String title);
}
