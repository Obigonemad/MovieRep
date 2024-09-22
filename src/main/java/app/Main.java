package app;

import app.config.HibernateConfig;
import app.dao.MovieDAO;
import app.dto.ActorDTO;
import app.dto.GenreDTO;
import app.dto.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.ObjectInputFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedatabase");

//
//        Actor a1 = new Actor("hansTheActor");
//        Director d1 = new Director("JohnTheDirector");
//        Movie m1 = new Movie("SpiderMan8", 10, LocalDate.of(2025,01,02));
//        Genre g1 = new Genre("Action");
//
//
        EntityManager em = emf.createEntityManager();

        // Opret DAO og Service
        MovieDAO movieDAO = MovieDAO.getInstance(emf);

        /* START: Gem i databasen */
        MovieService movieService = new MovieService(movieDAO);

        List<MovieDTO> allMoviesDTOs = new ArrayList<>();
        // Hent danske film med din eksisterende metode
        for (int page = 1; page<=66; page++) {
            allMoviesDTOs.addAll(movieService.getDanishMovies(page));
        }

        // Gem hver film i databasen
        for (MovieDTO movieDTO : allMoviesDTOs) {
            movieDAO.saveMovie(movieDTO);
        }


//        movieDAO.getAllMovieTitles();
//        MovieDTO m1 = new MovieDTO("bobo", LocalDate.of(2020,01,01));
////
//        movieDAO.createMovieFromMain(m1);
//        movieDAO.updateEntity(789,"bobobo");
//        movieDAO.deleteMovieById();

       

/*UDSKRIVER ALLE FILMTITLER I DATABASEN*/
        //movieDAO.getAllMovieTitles();


        /*MULIGHED FOR AT SØGE PÅ TITEL*/
//        List<MovieDTO> movies = MovieDAO.getMoviesByTitle("KÆRLIGHED");
//
//        for (MovieDTO movie : movies) {
//            System.out.println(movie.getTitle());
//
//        }



        /*AVARAGERATING*/
       // movieDAO.getAvarageRating();

        /*TOP10*/
        movieDAO.getTop10Rated();

        /*Bottom10*/
        movieDAO.getBottom10Rated();
        emf.close();
    }
}


//        try {
//            /* Kald metoden fetchDanishMovies() for at hente listen over danske film */
//            List<MovieDTO> movieDTOs = movieService.getDanishMovies();
//
//            /* Print hver film som JSON */
//            for (MovieDTO movie : movieDTOs) {
//                // Konverter MovieDTO til JSON-streng
//                String jsonString = objectMapper.writeValueAsString(movie);
//                System.out.println(jsonString);
//            }
//
//        } catch (Exception e) {
//
//            System.err.println("Der opstod en fejl: " + e.getMessage());
//            e.printStackTrace();
//        }

//        try {
//            /* Kalder metoden getDanishMovies() for at hente listen over danske film */
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


