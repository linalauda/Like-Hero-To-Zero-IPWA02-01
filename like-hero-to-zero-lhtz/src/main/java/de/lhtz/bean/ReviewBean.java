package de.lhtz.bean;

import de.lhtz.entity.Co2Entry;
import de.lhtz.service.Co2Service;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ReviewBean implements Serializable {

    @Inject
    private Co2Service co2Service;

    private List<Co2Entry> pendingEntries;

    public void init() {
        pendingEntries = co2Service.getPendingEntries();
    }

    public List<Co2Entry> getPendingEntries() {
        if (pendingEntries == null) {
            init();
        }
        return pendingEntries;
    }

    public void approve(Co2Entry entry) {
        co2Service.approveEntry(entry.getId());
        init();
    }

    public void reject(Co2Entry entry) {
        co2Service.rejectEntry(entry.getId());
        init();
    }
}