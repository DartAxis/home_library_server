package ru.dartinc.library_server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartinc.library_server.security.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
}
