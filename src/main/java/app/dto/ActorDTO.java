package app.dto;

import app.entities.Actor;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {
    private int id;
    private String name;
    private List<MovieDTO> actorInMovies;



    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        for (Movie movie : actor.getMovies()) {
            actorInMovies.add(new MovieDTO(movie));
        }
        this.actorInMovies = actorInMovies;
    }
}
