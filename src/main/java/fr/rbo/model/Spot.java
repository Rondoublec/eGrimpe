package fr.rbo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idSpot", nullable = false, unique = true)
    private Long idSpot;

    @NotBlank (message = "ERREUR : Le nom du spot est obligatoire.")
    @Size(max = 40, message = "ERREUR : Le nom du spot ne doit pas faire plus de 40 caractères")
    @Column(nullable = false, length = 40)
    private String nomSpot;

    @Column(nullable = true)
    private int interet;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDeMiseAJour;

    @Column(length = 500)
    @Size(max = 500, message = "ERREUR : taille du champs > 500 caractères")
    private String presentation;

    @Column
    private int hauteurMin;

    @Column
    private int hauteurMax;

    @Column(length = 50)
    @Size(max = 50, message = "ERREUR : taille du champs > 50 caractères")
    private String roche;

    @Column(length = 2)
    @Size(max = 2, message = "ERREUR : taille du champs > 2 caractères")
    private String cotationMin;

    @Column(length = 2)
    @Size(max = 2, message = "ERREUR : taille du champs > 2 caractères")
    private String cotationMax;

    @Column
    private boolean labelAmi;

    @Column(length = 5)
    @Size(max = 5, message = "ERREUR : taille du champs > 5 caractères")
    private String codePostalSpot;

    @Column(length = 50)
    @Size(max = 50, message = "ERREUR : taille du champs > 50 caractères")
    private String communeSpot;

    @Column(length = 50)
    @Size(max = 50, message = "ERREUR : taille du champs > 50 caractères")
    private String massif;

    @Column(length = 20)
    @Size(max = 20, message = "ERREUR : taille du champs > 20 caractères")
    private String orientation;

    @Column(length = 50)
    @Size(max = 50, message = "ERREUR : taille du champs > 50 caractères")
    private String acces;

    @Column(length = 50)
    @Size(max = 50, message = "ERREUR : taille du champs > 50 caractères")
    private String cartographie;

    @OneToMany(mappedBy="spot")
    private List<Commentaire> commentaires;

    @OneToMany(mappedBy="spot")
    private List<Secteur> secteurs;

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public Long getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(Long idSpot) {
        this.idSpot = idSpot;
    }

    public String getNomSpot() {
        return nomSpot;
    }

    public void setNomSpot(String nomSpot) {
        this.nomSpot = nomSpot;
    }

    public int getInteret() {
        return interet;
    }

    public void setInteret(int interet) {
        this.interet = interet;
    }

    public Date getDateDeMiseAJour() {
        return dateDeMiseAJour;
    }

    public void setDateDeMiseAJour(Date dateDeMiseAJour) {
        this.dateDeMiseAJour = dateDeMiseAJour;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public int getHauteurMin() {
        return hauteurMin;
    }

    public void setHauteurMin(int hauteurMin) {
        this.hauteurMin = hauteurMin;
    }

    public int getHauteurMax() {
        return hauteurMax;
    }

    public void setHauteurMax(int hauteurMax) {
        this.hauteurMax = hauteurMax;
    }

    public String getRoche() {
        return roche;
    }

    public void setRoche(String roche) {
        this.roche = roche;
    }

    public String getCotationMin() {
        return cotationMin;
    }

    public void setCotationMin(String cotationMin) {
        this.cotationMin = cotationMin;
    }

    public String getCotationMax() {
        return cotationMax;
    }

    public void setCotationMax(String cotationMax) {
        this.cotationMax = cotationMax;
    }

    public boolean isLabelAmi() {
        return labelAmi;
    }

    public void setLabelAmi(boolean labelAmi) {
        this.labelAmi = labelAmi;
    }

    public String getCodePostalSpot() {
        return codePostalSpot;
    }

    public void setCodePostalSpot(String codePostalSpot) {
        this.codePostalSpot = codePostalSpot;
    }

    public String getCommuneSpot() {
        return communeSpot;
    }

    public void setCommuneSpot(String communeSpot) {
        this.communeSpot = communeSpot;
    }

    public String getMassif() {
        return massif;
    }

    public void setMassif(String massif) {
        this.massif = massif;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    public String getCartographie() {
        return cartographie;
    }

    public void setCartographie(String cartographie) {
        this.cartographie = cartographie;
    }

}