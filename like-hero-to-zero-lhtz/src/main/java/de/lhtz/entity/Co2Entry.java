package de.lhtz.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "co2_entry")
@NamedQueries({
        @NamedQuery(name = "Co2Entry.findAll", query = "SELECT c FROM Co2Entry c ORDER BY c.year DESC"),
        @NamedQuery(name = "Co2Entry.findLatestByCountry", query = "SELECT c FROM Co2Entry c WHERE c.country.id = :countryId AND c.status = de.lhtz.entity.Co2Entry$EntryStatus.APPROVED ORDER BY c.year DESC"),
        @NamedQuery(name = "Co2Entry.findPending", query = "SELECT c FROM Co2Entry c WHERE c.status = de.lhtz.entity.Co2Entry$EntryStatus.PENDING ORDER BY c.createdAt ASC")
})
public class Co2Entry {

    public enum EntryStatus {
        PENDING, APPROVED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "co2_kt", nullable = false)
    private double co2Kt;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EntryStatus status = EntryStatus.PENDING;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Co2Entry() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getCo2Kt() { return co2Kt; }
    public void setCo2Kt(double co2Kt) { this.co2Kt = co2Kt; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public EntryStatus getStatus() { return status; }
    public void setStatus(EntryStatus status) { this.status = status; }
}