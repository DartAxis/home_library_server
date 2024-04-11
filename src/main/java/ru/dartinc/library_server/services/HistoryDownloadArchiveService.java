package ru.dartinc.library_server.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dartinc.library_server.repository.HistoryDownloadArchiveRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoryDownloadArchiveService {
    private final HistoryDownloadArchiveRepository repository;


}
