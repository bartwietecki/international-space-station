package repository;

import jakarta.persistence.EntityManager;
import db.DbConnection;
import entity.Location;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationRepository {
    public void insert(Location location) {
        EntityManager entityManager = DbConnection.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(location);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<Location> findAll() {
        EntityManager entityManager = DbConnection.getEntityManager();
        List<Location> location = entityManager.createQuery("select a from Location a", Location.class).getResultList();
        entityManager.close();
        return new HashSet<>(location);
    }

    public Location findById(long selectedLocationId) {
        EntityManager entityManager = DbConnection.getEntityManager();
        Location location = entityManager.find(Location.class, selectedLocationId);
        entityManager.close();
        return location;
    }
}
