package de.lhtz.repository;

import de.lhtz.entity.Publisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PublisherRepository {

    @PersistenceContext(unitName = "lhtzPU")
    private EntityManager em;

    public Publisher findByUsername(String username) {
        try {
            return em.createNamedQuery("Publisher.findByUsername", Publisher.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}