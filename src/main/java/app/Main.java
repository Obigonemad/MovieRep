package app;

import app.config.HibernateConfig;
import app.dto.ActorDTO;
import app.dto.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.services.MovieService;
import app.services.MovieService1;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedatabase");
//        EntityManager em = emf.createEntityManager();
//
//        Actor a1 = new Actor("hansTheActor");
//        Director d1 = new Director("JohnTheDirector");
//        Movie m1 = new Movie("SpiderMan8", 10, LocalDate.of(2025,01,02));
//        Genre g1 = new Genre("Action");
//
//
//        em.getTransaction().begin();
//        em.persist(a1);
//        em.persist(d1);
//        em.persist(m1);
//        em.persist(g1);
//        em.getTransaction().commit();
//        em.close();

        MovieService movieService = new MovieService();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            /* Kald metoden fetchDanishMovies() for at hente listen over danske film */
            List<MovieDTO> movieDTOs = movieService.getDanishMovies();

            /* Print hver film som JSON */
            for (MovieDTO movie : movieDTOs) {
                // Konverter MovieDTO til JSON-streng
                String jsonString = objectMapper.writeValueAsString(movie);
                System.out.println(jsonString);
            }

        } catch (Exception e) {
            // Håndter eventuelle fejl
            System.err.println("Der opstod en fejl: " + e.getMessage());
            e.printStackTrace();
        }

//        try {
//            /* Kalder metoden getDanishMovies() for at hente listen over danske film
//            List<MovieDTO> movieDTOs = movieService.getDanishMovies();
//
//            /* Print for hver film */
//            for (MovieDTO movie : movieDTOs) {
//                System.out.println("Film ID: " + movie.getId());
//                System.out.println("Title: " + movie.getTitle());
//                System.out.println("Rating: " + movie.getRating());
//                System.out.println("Release Date: " + movie.getReleaseDate());
//
//                /* Print genrer */
//                System.out.println("Genres:");
//                if (movie.getGenre() != null) {
//                    for (GenreDTO genre : movie.getGenre()) {
//                        System.out.println(" - " + genre.getName());
//                    }
//                }
//
//                /* Print skuespillere */
//                System.out.println("Actors:");
//                if (movie.getActors() != null) {
//                    for (ActorDTO actor : movie.getActors()) {
//                        System.out.println(" - " + actor.getName());
//                    }
//                }
//
//                /* Print instruktør */
//                System.out.println("Director:");
//                if (movie.getDirector() != null) {
//                    System.out.println(" - " + movie.getDirector().getName());
//                }
//
//                System.out.println("------------------------------------");
//            }
//
//        } catch (Exception e) {
//            // Håndter eventuelle fejl
//            System.err.println("Der opstod en fejl: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
    }
}
