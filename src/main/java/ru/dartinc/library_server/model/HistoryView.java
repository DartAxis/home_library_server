package ru.dartinc.library_server.model;

import jakarta.persistence.*;
import ru.dartinc.library_server.security.model.User;

import java.time.LocalDateTime;

@Entity(name = "view_history")
public class HistoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private LocalDateTime viewDate;
}
