package app;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.services.MovieService1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

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

        MovieService1 movieService1 = new MovieService1();
        movieService1.fetchDanishMovies();
    }
}