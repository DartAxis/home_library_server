package ru.dartinc.library_server.model.systemmodels;

import lombok.*;

import javax.persistence.*;
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
