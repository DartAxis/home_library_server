package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.dto.SeriaDTO;
import ru.dartinc.library_server.model.Seria;
import ru.dartinc.library_server.repository.SeriaRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SeriaService {
    private final SeriaRepository repository;

    public Seria saveSeria(Seria seria) {
        return repository.save(seria);
    }

    public List<Seria> getAllSeries() {
        return repository.findAll();
    }

    public List<SeriaDTO> getAllSeriesToFront() {
        var series = getAllSeries();
        if (series != null && !series.isEmpty()) {
            return series.stream().map(SeriaDTO::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Seria getSeriaById(Long id){
        var result = repository.findById(id);
        if(result.isPresent()){
            log.info("Найдена существующая серия с таким id={} seriaTitle=\"{}\"",id,result.get().getSeriaTitle());
            return result.get();
        }
        return null;
    }

    public Seria getSeriaBySeriaTitle(String seriaTitle){
        var result = repository.getSeriaBySeriaTitleIgnoreCase(seriaTitle);
        if(result!=null){
            log.info("Найдена существующая серия с таким seriaTitle={}",seriaTitle);
        }
        return result;
    }

    public Seria fromDTO(SeriaDTO seriaDTO){
        Seria seria;

        if(seriaDTO.getId()!=null){
            seria = getSeriaById(seriaDTO.getId());
            if(seria != null){
                return seria;
            }
        }

        seria = getSeriaBySeriaTitle(seriaDTO.getSeriaTitle());
        if (seria != null) {
            return seria;
        }

        seria = new Seria();
        seria.setSeriaTitle(seriaDTO.getSeriaTitle());
        log.info("Сохраняем новую серию с таким title={}",seria.getSeriaTitle());
        return saveSeria(seria);
    }

    public SeriaDTO add(SeriaDTO seriaDTO){
        log.info("*Добавляем новое издательство*");
        if(seriaDTO.getSeriaTitle()!=null && !seriaDTO.getSeriaTitle().isEmpty() && !seriaDTO.getSeriaTitle().isBlank()) {
            return new SeriaDTO(fromDTO(seriaDTO));
        } else {
            return null;
        }
    }

    public Seria add(String genreTitle){
        var result = getSeriaBySeriaTitle(genreTitle);
        if(result==null){
            result=new Seria();
            result.setSeriaTitle(genreTitle);
            repository.save(result);
        }
        return result;
    }

    public SeriaDTO editSeria(SeriaDTO seriaDTO){
        if(seriaDTO.getId()!=null && seriaDTO.getSeriaTitle()!=null && !seriaDTO.getSeriaTitle().isEmpty() && !seriaDTO.getSeriaTitle().isBlank()){
            var seria = getSeriaById(seriaDTO.getId());
            if(seria!=null){
                log.info("Редактируем серию с таким id={} title={}",seria.getId(),seria.getSeriaTitle());
                if(!seriaDTO.getSeriaTitle().equals(seria.getSeriaTitle())) {
                    seria.setSeriaTitle(seriaDTO.getSeriaTitle());
                    log.info("Сохраняем серию с таким id={} title={}",seria.getId(),seria.getSeriaTitle());
                    return new SeriaDTO(repository.save(seria));
                } else {
                    log.info("Серия с таким id={} title={} не изменилось",seria.getId(),seria.getSeriaTitle());
                    return new SeriaDTO(seria);
                }
            }
        }
        return null;
    }
}
