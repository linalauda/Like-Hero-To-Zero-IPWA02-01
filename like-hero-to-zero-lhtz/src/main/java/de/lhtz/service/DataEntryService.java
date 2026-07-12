package de.lhtz.service;

import de.lhtz.entity.Co2Entry;
import de.lhtz.entity.Country;
import de.lhtz.repository.Co2Repository;
import de.lhtz.repository.CountryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DataEntryService {

    @Inject
    private Co2Repository co2Repository;

    @Inject
    private CountryRepository countryRepository;

    public void addCo2Entry(Co2Entry entry) {
        if (entry.getCountry() == null) {
            throw new IllegalArgumentException("Land darf nicht leer sein");
        }
        if (entry.getYear() <= 0) {
            throw new IllegalArgumentException("Jahr muss größer als 0 sein");
        }
        if (entry.getCo2Kt() < 0) {
            throw new IllegalArgumentException("CO2-Wert darf nicht negativ sein");
        }
        co2Repository.save(entry);
    }

    public void updateCo2Entry(Co2Entry entry) {
        co2Repository.save(entry);
    }

    public void deleteCo2Entry(Co2Entry entry) {
        co2Repository.delete(entry);
    }
}