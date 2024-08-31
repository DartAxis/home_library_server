package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Book;
import ru.dartinc.library_server.model.HistoryDownloadArchive;
import ru.dartinc.library_server.security.model.User;

import java.util.List;

@Repository
public interface HistoryDownloadArchiveRepository extends JpaRepository<HistoryDownloadArchive,Long> {
    List<HistoryDownloadArchive> getHistoryDownloadArchivesByUser(User user);
    List<HistoryDownloadArchive> getHistoryDownloadArchivesByBook(Book book);
}
