package de.lhtz.repository;

import de.lhtz.entity.Country;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class CountryRepository {

    @PersistenceContext(unitName = "lhtzPU")
    private EntityManager em;

    public List<Country> findAll() {
        return em.createNamedQuery("Country.findAll", Country.class)
                .getResultList();
    }

    public Country findByIsoCode(String code) {
        try {
            return em.createNamedQuery("Country.findByCode", Country.class)
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Country findById(Long id) {
        return em.find(Country.class, id);
    }

    public void save(Country country) {
        if (country.getId() == null) {
            em.persist(country);
        } else {
            em.merge(country);
        }
    }
}