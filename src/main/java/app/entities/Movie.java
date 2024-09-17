package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private double rating;
    private LocalDate releaseDate;

    @OneToMany (mappedBy = "movie")
    private List<Genre> genre = new ArrayList<>();

    @ManyToOne
    private Director director;

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    public Movie(String title, double rating, LocalDate releaseDate) {
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
}
