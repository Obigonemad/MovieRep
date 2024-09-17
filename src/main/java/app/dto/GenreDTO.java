package app.dto;

import lombok.Data;

@Data
public class GenreDTO {
    private int id;
    private String name;

    public GenreDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }
}