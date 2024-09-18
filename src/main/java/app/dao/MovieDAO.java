package app.dao;

import app.dto.ActorDTO;
import app.dto.GenreDTO;
import app.dto.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    private static MovieDAO instance;
    private static EntityManagerFactory emf;

    public MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public MovieDAO() {
    }

    public static MovieDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new MovieDAO(emf);
        }
        return instance;
    }

    public void saveMovie(MovieDTO movieDTO) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Hent eller opret Director
            Director director = findOrCreateDirector(em, movieDTO.getDirector().getName());

            // Hent eller opret Actors
            List<Actor> actors = new ArrayList<>();
            for (ActorDTO actorDTO : movieDTO.getActors()) {
                Actor actor = findOrCreateActor(em, actorDTO.getName());
                actors.add(actor);
            }

            // Opret eller opdater Movie
            Movie movie = new Movie();
            movie.setTitle(movieDTO.getTitle());
            movie.setRating(movieDTO.getRating());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setDirector(director);
            movie.setActors(actors);

            em.persist(movie);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private Director findOrCreateDirector(EntityManager em, String name) {
        Director director = em.createQuery("SELECT d FROM Director d WHERE d.name = :name", Director.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (director == null) {
            director = new Director(name);
            em.persist(director);
        }
        return director;
    }

    private Actor findOrCreateActor(EntityManager em, String name) {
        Actor actor = em.createQuery("SELECT a FROM Actor a WHERE a.name = :name", Actor.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (actor == null) {
            actor = new Actor(name);
            em.persist(actor);
        }
        return actor;
    }
}


