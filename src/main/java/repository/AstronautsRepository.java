package repository;

import jakarta.persistence.EntityManager;
import db.DbConnection;
import entity.Astronauts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AstronautsRepository {
    public void insert(Astronauts astronauts) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(astronauts);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Astronauts> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Astronauts> astronauts = entityManager.createQuery("select a from Astronauts a", Astronauts.class).getResultList();
        entityManager.close();
        return new HashSet<>(astronauts);
    }

    public Astronauts findById(long selectedAstronautsId) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Astronauts astronauts = entityManager.find(Astronauts.class, selectedAstronautsId);
        entityManager.close();
        return astronauts;
    }
}
