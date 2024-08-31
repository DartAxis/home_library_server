package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.Book;
import ru.dartinc.library_server.model.HistoryView;
import ru.dartinc.library_server.security.model.User;

import java.util.List;

@Repository
public interface HistoryViewRepository extends JpaRepository<HistoryView,Long> {
    List<HistoryView> getHistoryViewsByUser(User user);
    List<HistoryView> getHistoryViewsByBook(Book book);
}
