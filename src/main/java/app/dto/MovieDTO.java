package app.dto;

import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("original_title")
    private String title;
    @JsonProperty("vote_average")
    private double rating;


    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private List<GenreDTO> genre;
    private List<ActorDTO> actors;
    private DirectorDTO director;


    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.releaseDate = movie.getReleaseDate();
        this.genre = new GenreDTO(movie.getGenre());
        this.actors = new ActorDTO(movie.getActors());
        this.director = new DirectorDTO(movie.getDirector());
    }
}



