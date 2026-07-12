package de.lhtz.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "country")
@NamedQueries({
        @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c ORDER BY c.name"),
        @NamedQuery(name = "Country.findByCode", query = "SELECT c FROM Country c WHERE c.isoCode = :code")
})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "iso_code", nullable = false, unique = true)
    private String isoCode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Co2Entry> co2Entries;

    public Country() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIsoCode() { return isoCode; }
    public void setIsoCode(String isoCode) { this.isoCode = isoCode; }

    public List<Co2Entry> getCo2Entries() { return co2Entries; }
    public void setCo2Entries(List<Co2Entry> co2Entries) { this.co2Entries = co2Entries; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id != null && id.equals(country.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}