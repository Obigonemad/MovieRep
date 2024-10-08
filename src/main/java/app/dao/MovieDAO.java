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
import jakarta.persistence.TypedQuery;

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

    //hej
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

    public void getAllMovieTitles() {
        try (EntityManager em = emf.createEntityManager()) {
            // Query to select only the title column
            List<String> titles = em.createQuery("SELECT m.title FROM Movie m", String.class)
                    .getResultList();

            // Print each title
            for (String title : titles) {
                System.out.println(title);
            }
        }
    }


    public static List<MovieDTO> getMoviesByTitle(String title) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE :title", Movie.class)
                    .setParameter("title", "%" + title.toLowerCase() + "%")
                    .getResultList();

            List<MovieDTO> movieDTOs = new ArrayList<>();
            for (Movie movie : movies) {
                movieDTOs.add(new MovieDTO(movie));
            }
            return movieDTOs;
        }
    }

    public static Double getAvarageRating() {
        try (EntityManager em = emf.createEntityManager()) {
            Double avarageRating = em.createQuery("SELECT AVG(m.rating) FROM Movie m WHERE m.rating > 0", Double.class)
                    .getSingleResult();
            System.out.println("Gennemsnitligt rating på alle film: ");
            System.out.println(avarageRating);
            return avarageRating;

        }
    }

    public static List<MovieDTO> getTop10Rated() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> topRatedMovies = em.createQuery("SELECT m FROM Movie m WHERE m.rating > 0 ORDER BY m.rating DESC", Movie.class)
                    .setMaxResults(10) // Begræns resultaterne til de øverste 10 film
                    .getResultList();

            List<MovieDTO> topMovieDTOs = new ArrayList<>();
            for (Movie movie : topRatedMovies) {
                topMovieDTOs.add(new MovieDTO(movie));

            }

            for (MovieDTO movieDTO : topMovieDTOs) {
                System.out.println("Title: " + movieDTO.getTitle() + ", Rating: " + movieDTO.getRating());
            }
            return topMovieDTOs;
        }
    }

    public static List<MovieDTO> getBottom10Rated() {
        try (EntityManager em = emf.createEntityManager()) {
            List<Movie> bottomRatedMovies = em.createQuery("SELECT m FROM Movie m WHERE m.rating > 0 ORDER BY m.rating ASC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();

            List<MovieDTO> bottomMovieDTOs = new ArrayList<>();
            for (Movie movie : bottomRatedMovies) {
                bottomMovieDTOs.add(new MovieDTO(movie));
            }

            for (MovieDTO movieDTO : bottomMovieDTOs) {
                System.out.println("Title: " + movieDTO.getTitle() + ", Rating: " + movieDTO.getRating());
            }

            return bottomMovieDTOs;
        }
    }


    public MovieDTO createMovieFromMain(MovieDTO movieDTO) {
        Movie movie = new Movie(movieDTO.getTitle(), movieDTO.getReleaseDate());

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Persist the movie
            em.persist(movie);
            em.getTransaction().commit();
        }
        return new MovieDTO(movie);
    }

    public int deleteMovieById(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie deleted = em.find(Movie.class, id);
            em.remove(deleted);
            em.getTransaction().commit();
            return deleted.getId();
        }
    }

    public MovieDTO updateEntity(Integer id, String updatedTitel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new IllegalArgumentException("Movie with id " + id + " not found.");
            }

            // Update the title
            movie.setTitle(updatedTitel);

            // Merge the updated entity back into the database
            em.merge(movie);

            em.getTransaction().commit();

            // Convert Movie to MovieDTO (assuming you have a conversion method)
            return new MovieDTO(movie);
        }
    }
}
