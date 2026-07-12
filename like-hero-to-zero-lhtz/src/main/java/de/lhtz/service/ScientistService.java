package de.lhtz.service;

import de.lhtz.entity.Scientist;
import de.lhtz.repository.ScientistRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class ScientistService {

    @Inject
    private ScientistRepository scientistRepository;

    public Scientist authenticate(String username, String password) {
        Scientist scientist = scientistRepository.findByUsername(username);
        if (scientist == null) return null;
        if (BCrypt.checkpw(password, scientist.getPasswordHash())) {
            return scientist;
        }
        return null;
    }

    public void register(String username, String password, String email) {
        Scientist scientist = new Scientist();
        scientist.setUsername(username);
        scientist.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        scientist.setEmail(email);
        scientistRepository.save(scientist);
    }
}