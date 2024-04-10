package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.model.Book;
import ru.dartinc.library_server.model.HistoryView;
import ru.dartinc.library_server.repository.HistoryViewRepository;
import ru.dartinc.library_server.security.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryViewService {
    private final HistoryViewRepository repository;

    public HistoryView save(HistoryView historyView){
        if(historyView!=null){
            return repository.save(historyView);
        }
        return null;
    }

    public List<HistoryView> getByUser(User user){
        return repository.getHistoryViewsByUser(user);
    }

    public List<HistoryView> getByBook(Book book){
        return repository.getHistoryViewsByBook(book);
    }
}
