package de.lhtz.service;

import de.lhtz.entity.Co2Entry;
import de.lhtz.entity.Country;
import de.lhtz.repository.Co2Repository;
import de.lhtz.repository.CountryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class Co2Service {

    @Inject
    private Co2Repository co2Repository;

    @Inject
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Co2Entry getLatestForCountry(Long countryId) {
        List<Co2Entry> entries = co2Repository.findLatestByCountry(countryId);
        return entries.isEmpty() ? null : entries.get(0);
    }

    public Co2Entry getLatestForCountryByCode(String isoCode) {
        Country country = countryRepository.findByIsoCode(isoCode);
        if (country == null) return null;
        return getLatestForCountry(country.getId());
    }

    public List<Co2Entry> getAllEntries() {
        return co2Repository.findAll();
    }
}