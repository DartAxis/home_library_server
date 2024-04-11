package ru.dartinc.library_server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.security.model.User;

import java.time.LocalDateTime;

@Entity(name = "view_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", unique = false)
    private User user;
    @OneToOne
    @JoinColumn(name = "book_id", unique = false)
    private Book book;
    private LocalDateTime viewDate;
}
