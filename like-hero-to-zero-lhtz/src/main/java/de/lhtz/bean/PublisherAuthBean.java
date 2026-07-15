package de.lhtz.bean;

import de.lhtz.entity.Publisher;
import de.lhtz.service.PublisherService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PublisherAuthBean implements Serializable {

    @Inject
    private PublisherService publisherService;

    private String username;
    private String password;
    private Publisher loggedInPublisher;

    public String login() {
        Publisher publisher = publisherService.authenticate(username, password);
        if (publisher != null) {
            loggedInPublisher = publisher;
            return "/publisher/review.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ungültige Anmeldedaten", null));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return loggedInPublisher != null;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Publisher getLoggedInPublisher() { return loggedInPublisher; }
}