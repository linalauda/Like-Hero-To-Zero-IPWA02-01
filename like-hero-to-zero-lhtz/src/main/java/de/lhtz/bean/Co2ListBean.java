package de.lhtz.bean;

import de.lhtz.entity.Co2Entry;
import de.lhtz.entity.Country;
import de.lhtz.service.Co2Service;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class Co2ListBean implements Serializable {

    @Inject
    private Co2Service co2Service;

    private List<Country> countries;
    private Country selectedCountry;

    @PostConstruct
    public void init() {
        countries = co2Service.getAllCountries();
    }

    public Co2Entry getLatestEmission(Country country) {
        return co2Service.getLatestForCountry(country.getId());
    }

    public List<Country> getCountries() { return countries; }
    public void setCountries(List<Country> countries) { this.countries = countries; }

    public Country getSelectedCountry() { return selectedCountry; }
    public void setSelectedCountry(Country country) { this.selectedCountry = country; }
}