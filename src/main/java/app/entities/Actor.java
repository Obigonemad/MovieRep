package app.entities;

import app.dto.ActorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Actor {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany (mappedBy = "actors")
    private List<Movie> movies =new ArrayList<>();

    public Actor( String name) {

        this.name = name;
    }

    public Actor(ActorDTO actorDTO) {

        this.name = actorDTO.getName();
    }

    public Actor(Integer id, String name) {

        this.name = name;
    }
}
