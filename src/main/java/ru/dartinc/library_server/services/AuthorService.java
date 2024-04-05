package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dartinc.library_server.dto.AuthorDTO;
import ru.dartinc.library_server.model.Author;
import ru.dartinc.library_server.repository.AuthorRepository;
import ru.dartinc.library_server.utils.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository repository;

    public List<AuthorDTO> getAllAuthorsToFront(){
        return repository.findAll().stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    public List<AuthorDTO> getAllAuthorsToFrontBySurname(String surname){
        return repository.getAuthorBySurnameIgnoreCase("%"+surname+"%").stream().map(AuthorDTO::new).collect(Collectors.toList());
    }

    public Author getById(Long id){
        var result = repository.findById(id);
        if(result.isPresent()){
            log.info("Найден автор с таким id={} Surname=\"{}\" Name=\"{}\" Middlename=\"{}\"",id,result.get().getSurname(),result.get().getName(),result.get().getMiddlename());
            return result.get();
        }
        return null;
    }

    public Author getByFio(String surname, String name, String middlename){
        return repository.getAuthorBySurnameIgnoreCaseAndNameIgnoreCaseAndMiddlenameIgnoreCase(surname,name,middlename);
    }

    public Author fromDTO(AuthorDTO authorDTO){
        Author author = null;
        if(authorDTO.getId() != null){
            author = getById(authorDTO.getId());
            if(author !=null){
                return author;
            }
        }
        author = getByFio(authorDTO.getSurname(),authorDTO.getName(),authorDTO.getMiddlename());
        if(author == null) {
            author = new Author();
            author.setSurname(StringUtil.capitalizeString(authorDTO.getSurname()));
            author.setName(StringUtil.capitalizeString(authorDTO.getName()));
            author.setMiddlename(StringUtil.capitalizeString(authorDTO.getMiddlename()));
            return repository.save(author);
        }
        return author;
    }

    public AuthorDTO add(AuthorDTO authorDTO){
        return new AuthorDTO(fromDTO(authorDTO));
    }

    public Author add(String surname,String name,String middlename){
        var author = new Author();
        author.setSurname(StringUtil.capitalizeString(surname));
        author.setName(StringUtil.capitalizeString(name));
        if(middlename!=null && !middlename.isEmpty() && !middlename.isBlank()) {
            author.setMiddlename(StringUtil.capitalizeString(middlename));
        }
        return repository.save(author);
    }
}
