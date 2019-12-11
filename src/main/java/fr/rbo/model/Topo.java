package fr.rbo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Topo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idTopo", nullable = false, unique = true)
    private Long idTopo;

    @NotBlank (message = "ERREUR : Le nom de topo est obligatoire.")
    @Size(max = 40, message = "ERREUR : Le nom du topo ne doit pas faire plus de 40 caractères")
    @Column(name = "nomTopo", length = 40, nullable = false)
    private String nomTopo;

    @NotBlank (message = "ERREUR : Le département est obligatoire.")
    @Size(max = 3, message = "ERREUR : le département ne doit pas faire plus de 3 caractères")
    @Column(name = "departementTopo", length = 3, nullable = false)
    private String departementTopo;

    @NotBlank (message = "ERREUR : Le code postal est obligatoire.")
    @Size(max = 5, message = "ERREUR : le code postal ne doit pas faire plus de 5 caractères")
    @Column(name = "codePostalTopo", length = 5, nullable = false)
    private String codePostalTopo;

    @Size(max = 500, message = "ERREUR : La description ne doit pas faire plus de 500 caractères")
    @Size(max = 500, message = "ERREUR : taille du champs > 500 caractères")
    @Column(name = "descriptionTopo", length = 500)
    private String descriptionTopo;

    @Size(max = 40, message = "ERREUR : Le d'auteur ne doit pas faire plus de 40 caractères")
    @Size(max = 40, message = "ERREUR : taille du champs > 40 caractères")
    @Column(name = "auteurTopo", length = 40)
    private String auteurTopo;

    @Column(name = "disponibiliteTopo", nullable = false)
    private boolean disponibiliteTopo;

    @Column(name="dateEmpruntTopo")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateEmpruntTopo;

    @Column(name="dateFinEmpruntTopo")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateFinEmpruntTopo;

    @ManyToOne
    @JoinColumn(name="proprietaireTopo")
    private User proprietaireTopo;

    @ManyToOne
    @JoinColumn(name="emprunteurTopo")
    private User emprunteurTopo;


    public Long getIdTopo() {
        return idTopo;
    }

    public void setIdTopo(Long idTopo) {
        this.idTopo = idTopo;
    }

    public String getNomTopo() {
        return nomTopo;
    }

    public void setNomTopo(String nomTopo) {
        this.nomTopo = nomTopo;
    }

    public String getDepartementTopo() {
        return departementTopo;
    }

    public void setDepartementTopo(String departementTopo) {
        this.departementTopo = departementTopo;
    }

    public String getCodePostalTopo() {
        return codePostalTopo;
    }

    public void setCodePostalTopo(String codePostalTopo) {
        this.codePostalTopo = codePostalTopo;
    }

    public String getDescriptionTopo() {
        return descriptionTopo;
    }

    public void setDescriptionTopo(String descriptionTopo) {
        this.descriptionTopo = descriptionTopo;
    }

    public String getAuteurTopo() {
        return auteurTopo;
    }

    public void setAuteurTopo(String auteurTopo) {
        this.auteurTopo = auteurTopo;
    }

    public boolean isDisponibiliteTopo() {
        return disponibiliteTopo;
    }

    public void setDisponibiliteTopo(boolean disponibiliteTopo) {
        this.disponibiliteTopo = disponibiliteTopo;
    }

    public Date getDateEmpruntTopo() {
        return dateEmpruntTopo;
    }

    public void setDateEmpruntTopo(Date dateEmpruntTopo) {
        this.dateEmpruntTopo = dateEmpruntTopo;
    }

    public Date getDateFinEmpruntTopo() {
        return dateFinEmpruntTopo;
    }

    public void setDateFinEmpruntTopo(Date dateFinEmpruntTopo) {
        this.dateFinEmpruntTopo = dateFinEmpruntTopo;
    }

    public User getProprietaireTopo() {
        return proprietaireTopo;
    }

    public void setProprietaireTopo(User proprietaireTopo) {
        this.proprietaireTopo = proprietaireTopo;
    }

    public User getEmprunteurTopo() {
        return emprunteurTopo;
    }

    public void setEmprunteurTopo(User emprunteurTopo) {
        this.emprunteurTopo = emprunteurTopo;
    }

}
