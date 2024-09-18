package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenreDTO {
    private int id;
    private String name;

    public GenreDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }
}