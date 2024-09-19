package app.dto;

import app.entities.Actor;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<ActorDTO> actors;
    private DirectorDTO director;

    public MovieDTO(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.rating = movie.getRating();
        this.releaseDate = movie.getReleaseDate();

//        if (movie.getActors() != null) {
//            this.actors = movie.getActors().stream()
//                    .map(ActorDTO::new)   // Mapper hver skuespiller til ActorDTO
//                    .collect(Collectors.toList());
//        }

//        this.director = new DirectorDTO(movie.getDirector());

       

        //hej
    }
}



