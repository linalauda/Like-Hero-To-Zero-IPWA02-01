package de.lhtz.service;

import de.lhtz.entity.Publisher;
import de.lhtz.repository.PublisherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class PublisherService {

    @Inject
    private PublisherRepository publisherRepository;

    public Publisher authenticate(String username, String password) {
        Publisher publisher = publisherRepository.findByUsername(username);
        if (publisher == null) return null;
        if (BCrypt.checkpw(password, publisher.getPasswordHash())) {
            return publisher;
        }
        return null;
    }
}