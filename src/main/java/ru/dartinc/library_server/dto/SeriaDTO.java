package ru.dartinc.library_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.Seria;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeriaDTO {
    private Long id;
    private String seriaTitle;

    public SeriaDTO(Seria seria) {
        this.id = seria.getId();
        this.seriaTitle = seria.getSeriaTitle();
    }
}
