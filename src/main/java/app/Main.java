package app;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("moviedatabase");
        EntityManager em = emf.createEntityManager();

        Actor a1 = new Actor("hansTheActor");
        Director d1 = new Director("JohnTheDirector");
        Movie m1 = new Movie("SpiderMan8", 10, LocalDate.of(2025,01,02));



        em.getTransaction().begin();
        em.persist(a1);
        em.persist(d1);
        em.persist(m1);
        em.getTransaction().commit();
        em.close();
    }
}