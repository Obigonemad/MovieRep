package app.dto;

import app.entities.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {
    private int id;
    private String name;

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
    }
}
