package fr.rbo.model;

import javax.validation.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Voie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank (message = "ERREUR : Le nom de la voie est obligatoire.")
    @Size(max = 40, message = "ERREUR : Le nom de la voie ne doit pas faire plus de 40 caractères")
    @Column(nullable = false, length = 40)
    @NotBlank
    private String nom;

    @Size(max = 200, message = "ERREUR : La description ne doit pas faire plus de 200 caractères")
    @Column(length = 200)
    private String description;

    @NotBlank (message = "ERREUR : La cotation est obligatoire.")
    @Column(nullable = false, length = 2)
    private String cotation;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDeMiseAJour;

    @ManyToOne
    private Secteur secteur;

    @OneToMany(mappedBy="voie")
    private List<Longueur> longueurs;

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

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateDeMiseAJour() {
        return dateDeMiseAJour;
    }

    public void setDateDeMiseAJour(Date dateDeMiseAJour) {
        this.dateDeMiseAJour = dateDeMiseAJour;
    }

    public List<Longueur> getLongueurs() {
        return longueurs;
    }

    public void setLongueurs(List<Longueur> longueurs) {
        this.longueurs = longueurs;
    }
}