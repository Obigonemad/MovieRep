package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class GenreDTO {
    private int id;
    private String name;

    public GenreDTO(String name, int id) {
        this.name = name;
        this.id = id;
    }
}