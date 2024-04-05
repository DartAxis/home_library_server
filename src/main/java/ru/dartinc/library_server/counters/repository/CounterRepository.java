package ru.dartinc.library_server.counters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dartinc.library_server.counters.model.Counter;
@Repository
public interface CounterRepository extends JpaRepository<Counter,Long> {
    Counter getCounterByNameCounter(String name);
}
