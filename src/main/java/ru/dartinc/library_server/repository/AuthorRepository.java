package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("select a from Author a where lower(a.surname) like lower(:surname)")
    List<Author> getAuthorBySurnameIgnoreCase(@Param("surname") String surname);

    Author getAuthorBySurnameIgnoreCaseAndNameIgnoreCaseAndMiddlenameIgnoreCase(String surname,String name,String middlename);
}
