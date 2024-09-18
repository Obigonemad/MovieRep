package app.dto;

import app.entities.Actor;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {
    private String name;
    private List<MovieDTO> actorInMovies;



    public ActorDTO(Actor actor) {
        this.name = actor.getName();
        for (Movie movie : actor.getMovies()) {
            actorInMovies.add(new MovieDTO(movie));
        }
        this.actorInMovies = actorInMovies;
    }
}
