package de.lhtz.bean;

import de.lhtz.entity.Co2Entry;
import de.lhtz.entity.Country;
import de.lhtz.service.Co2Service;
import de.lhtz.service.DataEntryService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AdminBean implements Serializable {

    @Inject
    private Co2Service co2Service;

    @Inject
    private DataEntryService dataEntryService;

    private Co2Entry newEntry = new Co2Entry();
    private Co2Entry editingEntry;
    private List<Co2Entry> allEntries;
    private List<Country> countries;

    @PostConstruct
    public void init() {
        allEntries = co2Service.getAllEntries();
        countries = co2Service.getAllCountries();
    }

    public void saveEntry() {
        try {
            dataEntryService.addCo2Entry(newEntry);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Eintrag erfolgreich gespeichert!", null));
            newEntry = new Co2Entry();
            allEntries = co2Service.getAllEntries();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehler: " + e.getMessage(), null));
        }
    }

    public void prepareEdit(Co2Entry entry) {
        this.editingEntry = new Co2Entry();
        this.editingEntry.setId(entry.getId());
        this.editingEntry.setCountry(entry.getCountry());
        this.editingEntry.setYear(entry.getYear());
        this.editingEntry.setCo2Kt(entry.getCo2Kt());
    }

    public void saveEditedEntry() {
        try {
            dataEntryService.updateCo2Entry(editingEntry);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Eintrag erfolgreich aktualisiert!", null));
            allEntries = co2Service.getAllEntries();
            editingEntry = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehler: " + e.getMessage(), null));
        }
    }

    public void deleteEntry(Co2Entry entry) {
        try {
            dataEntryService.deleteCo2Entry(entry);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Eintrag erfolgreich gelöscht!", null));
            allEntries = co2Service.getAllEntries();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehler: " + e.getMessage(), null));
        }
    }

    public Co2Entry getNewEntry() { return newEntry; }
    public void setNewEntry(Co2Entry newEntry) { this.newEntry = newEntry; }

    public Co2Entry getEditingEntry() { return editingEntry; }
    public void setEditingEntry(Co2Entry editingEntry) { this.editingEntry = editingEntry; }

    public List<Co2Entry> getAllEntries() { return allEntries; }
    public List<Country> getCountries() { return countries; }
}