package ru.dartinc.library_server.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username",unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name = "isactive")
    private Boolean isActive = true;

    @Column(name = "enddateofsubscribe")
    private LocalDate subscribeDate = LocalDate.now();
}
