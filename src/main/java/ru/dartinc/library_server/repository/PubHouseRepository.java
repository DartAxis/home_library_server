package ru.dartinc.library_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.model.PubHouse;

@Repository
public interface PubHouseRepository extends JpaRepository<PubHouse,Long> {
    PubHouse getPubHouseByPubHouseNameIgnoreCase(String pubHouseName);
}
