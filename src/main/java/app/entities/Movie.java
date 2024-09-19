package app.entities;

import app.dto.GenreDTO;
import app.dto.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Movie {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private double rating;
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
        this.rating = movieDTO.getRating();
        this.releaseDate = movieDTO.getReleaseDate();

    }

    public Movie(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }
}
