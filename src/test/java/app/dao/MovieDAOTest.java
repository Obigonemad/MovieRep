package app.dao;

import app.config.HibernateConfig;
import app.dto.MovieDTO;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final MovieDAO movieDao = MovieDAO.getInstance(emf);
    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE movie_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown() {
        // Optionally, clean up after tests if needed
    }

    @Test
    void createMovieFromMain() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Create a new MovieDTO object
            MovieDTO m3 = new MovieDTO("brødrenebullerbob", LocalDate.of(2020, 1, 2));
            // Persist the movie using DAO
            MovieDTO createdMovieDTO = movieDao.createMovieFromMain(m3);

            // Commit the transaction to ensure the movie is saved
            em.getTransaction().commit();

            // Clear the EntityManager to ensure the query is fresh
            em.clear();

            // Retrieve the movie from the database
            Movie m3retrieved = em.find(Movie.class, createdMovieDTO.getId());

            // Verify the movie was created and retrieved correctly
            assertNotNull(m3retrieved, "Movie should not be null");
            assertEquals("brødrenebullerbob", m3retrieved.getTitle(), "Movie title should match");
            assertEquals(LocalDate.of(2020, 1, 2), m3retrieved.getReleaseDate(), "Movie release date should match");

        } catch (PersistenceException e) {
            fail("An error occurred while performing the test", e);
        } finally {
            em.close();
        }
    }


    @Test
    void deleteMovieById() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Create a new MovieDTO object and persist it
            MovieDTO m = new MovieDTO("deleteMe", LocalDate.of(2024, 1, 1));
            MovieDTO createdMovieDTO = movieDao.createMovieFromMain(m);

            em.getTransaction().commit(); // Commit to ensure the movie is saved
            em.clear(); // Clear the persistence context

            // Verify the movie exists in the database before deletion
            Movie movie = em.find(Movie.class, createdMovieDTO.getId());
            assertNotNull(movie, "Movie should be present before deletion");

            // Now delete the movie
            movieDao.deleteMovieById(createdMovieDTO.getId());

            // Clear the persistence context after the deletion
            em.clear();

            // Verify the movie has been deleted
            Movie deletedMovie = em.find(Movie.class, createdMovieDTO.getId());
            assertNull(deletedMovie, "Movie should be null after deletion");

        } finally {
            em.close();
        }
    }


    @Test
    void updateEntity() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Create and persist a new MovieDTO object
            MovieDTO m = new MovieDTO("originalTitle", LocalDate.of(2024, 1, 1));
            MovieDTO createdMovieDTO = movieDao.createMovieFromMain(m);

            em.getTransaction().commit(); // Commit to ensure the movie is saved
            em.clear(); // Clear the persistence context

            // Verify that the movie has the original title
            Movie movie = em.find(Movie.class, createdMovieDTO.getId());
            assertNotNull(movie, "Movie should be present before update");
            assertEquals("originalTitle", movie.getTitle(), "Movie title should match the original");

            // Update the movie title using the DAO method
            String updatedTitle = "updatedTitle";
            MovieDTO updatedMovieDTO = movieDao.updateEntity(createdMovieDTO.getId(), updatedTitle);

            // Clear the EntityManager to ensure fresh retrieval
            em.clear();

            // Retrieve the updated movie from the database
            Movie updatedMovie = em.find(Movie.class, updatedMovieDTO.getId());

            // Verify the updated details
            assertNotNull(updatedMovie, "Movie should still be present after update");
            assertEquals(updatedTitle, updatedMovie.getTitle(), "Movie title should match the updated value");
            assertEquals(createdMovieDTO.getId(), updatedMovie.getId(), "Movie ID should remain the same");

        } finally {
            em.close();
        }
    }

}