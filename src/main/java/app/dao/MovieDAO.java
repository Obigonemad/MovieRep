package app.dao;

import app.dto.MovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManagerFactory;

public class MovieDAO {

    private static MovieDAO instance;
    private static EntityManagerFactory emf;

    private MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static MovieDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new MovieDAO(emf);
        }
        return instance;
    }

    public Movie createMovie (MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO);

    }
}
