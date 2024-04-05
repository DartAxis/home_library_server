package ru.dartinc.library_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dartinc.library_server.model.PubHouse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PubHouseDTO {
    private Long id;
    private String pubHouseName;

    public PubHouseDTO(PubHouse pubHouse) {
        this.id = pubHouse.getId();
        this.pubHouseName = pubHouse.getPubHouseName();
    }
}
