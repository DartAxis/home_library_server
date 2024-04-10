package ru.dartinc.library_server.model;

import jakarta.persistence.*;
import ru.dartinc.library_server.security.model.User;

import java.time.LocalDateTime;

@Entity(name = "download_history")
public class HistoryDownloadArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "download_date")
    private LocalDateTime downloadArchiveDate;
}
