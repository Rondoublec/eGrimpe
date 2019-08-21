package fr.rbo.model;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Secteur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 40)
    @NotBlank
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDeMiseAJour;

    @ManyToOne
    private Spot spot;

    @OneToMany(mappedBy="secteur")
    private List<Voie> voies;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDeMiseAJour() {
        return dateDeMiseAJour;
    }

    public void setDateDeMiseAJour(Date dateDeMiseAJour) {
        this.dateDeMiseAJour = dateDeMiseAJour;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Integer getId() {
        return id;
    }

    public List<Voie> getVoies() {
        return voies;
    }

    public void setVoies(List<Voie> voies) {
        this.voies = voies;
    }
}