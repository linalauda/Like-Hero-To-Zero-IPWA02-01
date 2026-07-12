package de.lhtz.bean;

import de.lhtz.entity.Scientist;
import de.lhtz.service.ScientistService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    @Inject
    private ScientistService scientistService;

    private String username;
    private String password;
    private Scientist loggedInScientist;

    public String login() {
        Scientist scientist = scientistService.authenticate(username, password);
        if (scientist != null) {
            loggedInScientist = scientist;
            return "/admin/dashboard?faces-redirect=true";
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
        return loggedInScientist != null;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Scientist getLoggedInScientist() { return loggedInScientist; }
}