package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Seria;

@Repository
public interface SeriaRepository extends JpaRepository<Seria,Long> {
    Seria getSeriaBySeriaTitleIgnoreCase(String seriaTitle);
}
