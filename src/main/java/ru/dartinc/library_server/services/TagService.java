package ru.dartinc.library_server.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dartinc.library_server.dto.TagDTO;
import ru.dartinc.library_server.model.Tag;
import ru.dartinc.library_server.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository repository;

    public Tag saveTag(Tag tag){
        return repository.save(tag);
    }

    public List<Tag> getAllTags(){
        return repository.findAll();
    }

    public Tag getTagById(Long id){
        var result = repository.findById(id);
        if(result.isPresent()){
            log.info("Найден существующий таг с таким id={} title=\"{}\"",id,result.get().getTagTitle());
            return result.get();
        }
        return null;
    }

    public Tag getTagByTagTitle(String tagTitle){
        var result = repository.getTagByTagTitleIgnoreCase(tagTitle);
        if(result!=null){
            log.info("Найден существующий таг с таким title={}",tagTitle);
        }
        return result;
    }

    public TagDTO toDTO(Tag tag){
        return new TagDTO(tag);
    }

    public Tag fromDTO(TagDTO tagDTO){
        Tag tag;

        if(tagDTO.getId()!=null) {
            tag = getTagById(tagDTO.getId());
            if (tag != null) {
                return tag;
            }
        }

        tag = getTagByTagTitle(tagDTO.getTagTitle());
        if (tag != null) {
            return tag;
        }

        tag = new Tag();
        tag.setTagTitle(tagDTO.getTagTitle());
        log.info("Сохраняем новый таг с таким title={}",tag.getTagTitle());
        return saveTag(tag);
    }

    public List<TagDTO> getAllTagsToFront(){
        return repository.findAll().stream().map(TagDTO::new).collect(Collectors.toList());
    }

    public TagDTO add(TagDTO tagDTO){
        log.info("*Добавляем новый таг*");
        if(tagDTO.getTagTitle()!=null && !tagDTO.getTagTitle().isEmpty() && !tagDTO.getTagTitle().isBlank()) {
            return new TagDTO(fromDTO(tagDTO));
        } else {
            return null;
        }
    }
    public Tag add(String tagTitle){
        var result = getTagByTagTitle(tagTitle);
        if(result==null){
            result = new Tag();
            result.setTagTitle(tagTitle);
            repository.save(result);
        }
        return result;
    }

    public TagDTO editTag(TagDTO tagDTO){
        if(tagDTO.getId()!=null && tagDTO.getTagTitle()!=null && !tagDTO.getTagTitle().isEmpty() && !tagDTO.getTagTitle().isBlank()){
            var tag = getTagById(tagDTO.getId());
            if(tag!=null){
                log.info("Редактируем таг с таким id={} title={}",tag.getId(),tag.getTagTitle());
                if(!tagDTO.getTagTitle().equals(tag.getTagTitle())) {
                    tag.setTagTitle(tagDTO.getTagTitle());
                    log.info("Сохраняем таг с таким id={} title={}",tag.getId(),tag.getTagTitle());
                    return new TagDTO(repository.save(tag));
                } else {
                    log.info("Таг с таким id={} title={} не изменился",tag.getId(),tag.getTagTitle());
                    return new TagDTO(tag);
                }
            }
        }
        return null;
    }
}
