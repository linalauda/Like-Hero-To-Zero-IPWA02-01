package de.lhtz.converter;

import de.lhtz.entity.Country;
import de.lhtz.repository.CountryRepository;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "countryConverter", managed = true)
public class CountryConverter implements Converter<Country> {

    private CountryRepository getCountryRepository() {
        return CDI.current().select(CountryRepository.class).get();
    }

    @Override
    public Country getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            return getCountryRepository().findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Country value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}