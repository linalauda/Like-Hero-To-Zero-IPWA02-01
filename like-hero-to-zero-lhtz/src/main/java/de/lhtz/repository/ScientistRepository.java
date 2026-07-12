package de.lhtz.repository;

import de.lhtz.entity.Scientist;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScientistRepository {

    @PersistenceContext(unitName = "lhtzPU")
    private EntityManager em;

    public Scientist findByUsername(String username) {
        try {
            return em.createNamedQuery("Scientist.findByUsername", Scientist.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void save(Scientist scientist) {
        if (scientist.getId() == null) {
            em.persist(scientist);
        } else {
            em.merge(scientist);
        }
    }
}