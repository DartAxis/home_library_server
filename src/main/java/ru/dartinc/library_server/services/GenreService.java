package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.dto.GenreDTO;
import ru.dartinc.library_server.model.Genre;
import ru.dartinc.library_server.repository.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GenreService {
    private final GenreRepository repository;

    public Genre saveGenre(Genre genre){
        return repository.save(genre);
    }

    public List<Genre> getAllGenres(){
        return repository.findAll();
    }

    public Genre getGenreById(Long id){
        var result = repository.findById(id);
        if(result.isPresent()){
            log.info("Найден существующий жанр с таким id={} title=\"{}\"",id,result.get().getGenreTitle());
            return result.get();
        }
        return null;
    }

    public Genre getGenreByGenreTitle(String title){
        var result = repository.getGenreByGenreTitleIgnoreCase(title);
        if(result!=null){
            log.info("Найден существующий жанр с таким title={}",title);
        }
        return result;
    }

    public GenreDTO toDTO(Genre genre){
        return new GenreDTO(genre);
    }

    public Genre fromDTO(GenreDTO genreDTO){
        Genre genre;

        if(genreDTO.getId()!=null){
            genre = getGenreById(genreDTO.getId());
            if(genre != null){
                return genre;
            }
        }

        genre = getGenreByGenreTitle(genreDTO.getGenreTitle());
        if (genre != null) {
            return genre;
        }

        genre = new Genre();
        genre.setGenreTitle(genreDTO.getGenreTitle());
        log.info("Сохраняем новый жанр с таким title={}",genre.getGenreTitle());
        return saveGenre(genre);
    }

    public GenreDTO add(GenreDTO genreDTO){
        log.info("*Добавляем новый жанр*");
        if(genreDTO.getGenreTitle()!=null && !genreDTO.getGenreTitle().isEmpty() && !genreDTO.getGenreTitle().isBlank()) {
            return new GenreDTO(fromDTO(genreDTO));
        } else {
            return null;
        }
    }

    public Genre add(String genreTitle){
        var result = getGenreByGenreTitle(genreTitle);
        if(result==null){
            result=new Genre();
            result.setGenreTitle(genreTitle);
            repository.save(result);
        }
        return result;
    }
    public List<GenreDTO> getAllGenresToFront() {
        List<Genre> list = getAllGenres();
        if(list!=null && !list.isEmpty()){
            return list.stream().map(this::toDTO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public GenreDTO editGenre(GenreDTO genreDTO){
        if(genreDTO.getId()!=null && genreDTO.getGenreTitle()!=null && !genreDTO.getGenreTitle().isEmpty() && !genreDTO.getGenreTitle().isBlank()){
            var genre = getGenreById(genreDTO.getId());
            if(genre!=null){
                log.info("Редактируем жанр с таким id={} title={}",genre.getId(),genre.getGenreTitle());
                if(!genreDTO.getGenreTitle().equals(genre.getGenreTitle())) {
                    genre.setGenreTitle(genreDTO.getGenreTitle());
                    log.info("Сохраняем жанр с таким id={} title={}",genre.getId(),genre.getGenreTitle());
                    return new GenreDTO(repository.save(genre));
                } else {
                    log.info("Жанр с таким id={} title={} не изменился",genre.getId(),genre.getGenreTitle());
                    return new GenreDTO(genre);
                }
            }
        }
        return null;
    }
}
