package de.lhtz.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "scientist")
@NamedQueries({
        @NamedQuery(name = "Scientist.findAll", query = "SELECT s FROM Scientist s"),
        @NamedQuery(name = "Scientist.findByUsername", query = "SELECT s FROM Scientist s WHERE s.username = :username")
})
public class Scientist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email")
    private String email;

    public Scientist() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}