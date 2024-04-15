package ru.dartinc.library_server.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dartinc.library_server.model.Book;
import ru.dartinc.library_server.model.HistoryDownloadArchive;
import ru.dartinc.library_server.repository.HistoryDownloadArchiveRepository;
import ru.dartinc.library_server.security.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryDownloadArchiveService {
    private final HistoryDownloadArchiveRepository repository;

    public HistoryDownloadArchive save(HistoryDownloadArchive historyDownloadArchive){
        if(historyDownloadArchive!=null){
            return repository.save(historyDownloadArchive);
        }
        return null;
    }

    public List<HistoryDownloadArchive> getByUser(User user){
        return repository.getHistoryDownloadArchivesByUser(user);
    }

    public List<HistoryDownloadArchive> getByBook(Book book){
        return repository.getHistoryDownloadArchivesByBook(book);
    }


}
