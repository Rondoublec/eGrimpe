package fr.rbo.model;

import java.io.Serializable;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "adresse")
public class Adresse implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Adresse.class);

    @Id
    @Column(name="adresse_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name = "numero", length = 10, nullable = true)
    private String numero;
    @Column(name = "typeDeVoie", length = 20, nullable = true)
    private String typeDeVoie;
    @Column(name = "adresse1", length = 50, nullable = true)
    private String adresse1;
    @Column(name = "complementAdresse", length = 50, nullable = true)
    private String complementAdresse;
    @Column(name = "codePostal", length = 5, nullable = true)
    private String codePostal;
    @Column(name = "ville", length = 50, nullable = true)
    private String ville;
    @Column(name = "pays", length = 25, nullable = true)
    private String pays;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTypeDeVoie() {
        return typeDeVoie;
    }

    public void setTypeDeVoie(String typeDeVoie) {
        this.typeDeVoie = typeDeVoie;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getComplementAdresse() {
        return complementAdresse;
    }

    public void setComplement_adresse(String complement_adresse) {
        this.complementAdresse = complement_adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }


}
