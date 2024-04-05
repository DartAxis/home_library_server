package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.dto.GenreDTO;
import ru.dartinc.library_server.dto.PubHouseDTO;
import ru.dartinc.library_server.model.PubHouse;
import ru.dartinc.library_server.repository.PubHouseRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PubHouseService {
    private final PubHouseRepository repository;

    public PubHouse saveGenre(PubHouse pubHouse){
        return repository.save(pubHouse);
    }
    public List<PubHouse> getAllPubhouses(){
        return repository.findAll();
    }
    public List<PubHouseDTO> getAllPubHousesToFront(){
        var pubHouses= getAllPubhouses();
        if(pubHouses!=null && !pubHouses.isEmpty()){
            return pubHouses.stream().map(PubHouseDTO::new).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public PubHouse getPubHouseById(Long id){
        var result = repository.findById(id);
        if(result.isPresent()){
            log.info("Найдено существующее издательство с таким id={} title=\"{}\"",id,result.get().getPubHouseName());
            return result.get();
        }
        return null;
    }

    public PubHouse getPubHouseByPubHouseName(String pubHouseName){
        var result = repository.getPubHouseByPubHouseNameIgnoreCase(pubHouseName);
        if(result!=null){
            log.info("Найдено существующее издательство с таким pubHouseName={}",pubHouseName);
        }
        return result;
    }

    public PubHouse fromDTO(PubHouseDTO pubHouseDTO){
        PubHouse pubHouse;

        if(pubHouseDTO.getId()!=null){
            pubHouse = getPubHouseById(pubHouseDTO.getId());
            if(pubHouse != null){
                return pubHouse;
            }
        }

        pubHouse = getPubHouseByPubHouseName(pubHouseDTO.getPubHouseName());
        if (pubHouse != null) {
            return pubHouse;
        }

        pubHouse = new PubHouse();
        pubHouse.setPubHouseName(pubHouseDTO.getPubHouseName());
        log.info("Сохраняем новое издательство с таким pubHouseName={}",pubHouse.getPubHouseName());
        return saveGenre(pubHouse);
    }

    public PubHouseDTO add(PubHouseDTO pubHouseDto){
        log.info("*Добавляем новое издательство*");
        if(pubHouseDto.getPubHouseName()!=null && !pubHouseDto.getPubHouseName().isEmpty() && !pubHouseDto.getPubHouseName().isBlank()) {
            return new PubHouseDTO(fromDTO(pubHouseDto));
        } else {
            return null;
        }
    }

    public PubHouse add(String pubHouseName){
        var result = getPubHouseByPubHouseName(pubHouseName);
        if(result!=null){
            return result;
        } else {
            log.info("*Добавляем новое издательство*");
            result = new PubHouse();
            result.setPubHouseName(pubHouseName);
            return repository.save(result);
        }
    }

    public PubHouseDTO editPubHouse(PubHouseDTO pubHouseDTO){
        if(pubHouseDTO.getId()!=null && pubHouseDTO.getPubHouseName()!=null && !pubHouseDTO.getPubHouseName().isEmpty() && !pubHouseDTO.getPubHouseName().isBlank()){
            var pubHouse = getPubHouseById(pubHouseDTO.getId());
            if(pubHouse!=null){
                log.info("Редактируем издательство с таким id={} title={}",pubHouse.getId(),pubHouse.getPubHouseName());
                if(!pubHouseDTO.getPubHouseName().equals(pubHouse.getPubHouseName())) {
                    pubHouse.setPubHouseName(pubHouseDTO.getPubHouseName());
                    log.info("Сохраняем издательство с таким id={} title={}",pubHouse.getId(),pubHouse.getPubHouseName());
                    return new PubHouseDTO(repository.save(pubHouse));
                } else {
                    log.info("Издательство с таким id={} PubhouseName={} не изменилось",pubHouse.getId(),pubHouse.getPubHouseName());
                    return new PubHouseDTO(pubHouse);
                }
            }
        }
        return null;
    }

}
