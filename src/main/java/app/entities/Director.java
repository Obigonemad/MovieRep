package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Director {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany (mappedBy = "director")
    private Set<Movie> movies=new HashSet<>();

    public Director(String name) {
        this.name = name;

    }
}
