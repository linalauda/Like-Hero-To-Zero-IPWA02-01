package de.lhtz.bean;

import de.lhtz.entity.Co2Entry;
import de.lhtz.entity.Country;
import de.lhtz.repository.CountryRepository;
import de.lhtz.service.Co2Service;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;

@Named
@ViewScoped
public class CountryDetailBean implements Serializable {

    @Inject
    private Co2Service co2Service;

    @Inject
    private CountryRepository countryRepository;

    private String isoCode;
    private Country country;
    private Co2Entry latestEntry;

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        isoCode = params.get("code");
        if (isoCode != null) {
            country = countryRepository.findByIsoCode(isoCode);
            if (country != null) {
                latestEntry = co2Service.getLatestForCountry(country.getId());
            }
        }
    }

    public String getIsoCode() { return isoCode; }
    public void setIsoCode(String isoCode) { this.isoCode = isoCode; }

    public Country getCountry() { return country; }
    public Co2Entry getLatestEntry() { return latestEntry; }
}