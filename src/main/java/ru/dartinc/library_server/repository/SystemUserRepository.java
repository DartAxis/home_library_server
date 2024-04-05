package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.systemmodels.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser,Long> {
}
