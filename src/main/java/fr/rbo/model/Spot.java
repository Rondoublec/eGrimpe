package fr.rbo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idSpot", nullable = false, unique = true)
    private Long idSpot;

    @NotBlank (message = "Le nom du spot est obligatoire.")
    @Column(nullable = false, length = 40)
    private String nomSpot;

    @Column(nullable = true)
    private int interet;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date dateDeMiseAJour;

    @Column(length = 500)
    private String presentation;

    @Column
    private int hauteurMin;

    @Column
    private int hauteurMax;

    @Column(length = 50)
    private String roche;

    @Column(length = 2)
    private String cotationMin;

    @Column(length = 2)
    private String cotationMax;

    @Column
    private boolean labelAmi;

    @Column(length = 5)
    private String codePostalSpot;

    @Column(length = 50)
    private String communeSpot;

    @Column(length = 50)
    private String massif;

    @Column(length = 20)
    private String orientation;

    @Column(length = 50)
    private String acces;

    @Column(length = 50)
    private String cartographie;

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