package fr.rbo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Longueur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 40)
    private String nom;

    @Column(length = 200)
    private String description;

    @Column(length = 2)
    private String cotation;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDeMiseAJour;

    @ManyToOne
    private Voie voie;

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

    public String getCotation() {
        return cotation;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public Voie getVoie() {
        return voie;
    }

    public void setVoie(Voie voie) {
        this.voie = voie;
    }

    public Date getDateDeMiseAJour() {
        return dateDeMiseAJour;
    }

    public void setDateDeMiseAJour(Date dateDeMiseAJour) {
        this.dateDeMiseAJour = dateDeMiseAJour;
    }
}