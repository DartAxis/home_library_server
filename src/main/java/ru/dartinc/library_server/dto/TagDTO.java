package ru.dartinc.library_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Tag;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    private String tagTitle;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tagTitle = tag.getTagTitle();
    }
}
