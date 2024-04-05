package ru.dartinc.library_server.counters.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.counters.model.Counter;
import ru.dartinc.library_server.counters.repository.CounterRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CounterService {
    private final CounterRepository repository;

    public Counter getByName(String name){
        return repository.getCounterByNameCounter(name);
    }

    public Counter add(String name){
        var temp = getByName(name);
        if(temp != null){
            return temp;
        }
        var counter = new Counter();
        counter.setNameCounter(name);
        counter.setValueCounter(0L);
        return repository.save(counter);
    }

    public Counter incCounter(String name){
        var temp = getByName(name);
        if(temp != null){
            temp.setValueCounter(temp.getValueCounter()+1);
        } else {
            temp = add(name);
        }
        return repository.save(temp);
    }

}
