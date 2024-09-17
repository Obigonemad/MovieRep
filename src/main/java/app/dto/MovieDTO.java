package app.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieDTO {
private int id;
private String title;
private double rating;
private List<Genre> genre;
private LocalDate releaseDate;
private List<ActorDTO> actors;
private DirectorDTO director;


}
