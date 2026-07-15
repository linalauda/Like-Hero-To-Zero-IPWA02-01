package de.lhtz.repository;

import de.lhtz.entity.Co2Entry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class Co2Repository {

    @PersistenceContext(unitName = "lhtzPU")
    private EntityManager em;

    public List<Co2Entry> findAll() {
        return em.createNamedQuery("Co2Entry.findAll", Co2Entry.class)
                .getResultList();
    }

    public List<Co2Entry> findLatestByCountry(Long countryId) {
        return em.createNamedQuery("Co2Entry.findLatestByCountry", Co2Entry.class)
                .setParameter("countryId", countryId)
                .setMaxResults(1)
                .getResultList();
    }

    public List<Co2Entry> findPending() {
        return em.createNamedQuery("Co2Entry.findPending", Co2Entry.class)
                .getResultList();
    }

    @Transactional
    public void save(Co2Entry entry) {
        if (entry.getId() == null) {
            em.persist(entry);
        } else {
            em.merge(entry);
        }
    }


    @Transactional
    public void delete(Co2Entry entry) {
        Co2Entry managed = em.find(Co2Entry.class, entry.getId());
        if (managed != null) {
            em.remove(managed);
        }
    }
}