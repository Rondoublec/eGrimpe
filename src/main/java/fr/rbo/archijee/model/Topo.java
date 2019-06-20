package fr.rbo.archijee.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Topo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="idTopo", nullable = false, unique = true)
    private int idTopo;

    @Column(name = "nomTopo", length = 40, nullable = false)
    @NotBlank
    private String nomTopo;

    @Column(name = "departementTopo", length = 3, nullable = false)
    @NotBlank
    private String departementTopo;

    @Column(name = "codePostalTopo", length = 5, nullable = false)
    private String codePostalTopo;

    @Column(name = "descriptionTopo", length = 500)
    private String descriptionTopo;

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
    @JoinColumn(name="idPropriétaireTopo")
    private User idPropriétaireTopo;

    @ManyToOne
    @JoinColumn(name="idEmprunteurTopo")
    private User idEmprunteurTopo;


    public int getIdTopo() {
        return idTopo;
    }

    public void setIdTopo(int idTopo) {
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

    public User getIdPropriétaireTopo() {
        return idPropriétaireTopo;
    }

    public void setIdPropriétaireTopo(User idPropriétaireTopo) {
        this.idPropriétaireTopo = idPropriétaireTopo;
    }

    public User getIdEmprunteurTopo() {
        return idEmprunteurTopo;
    }

    public void setIdEmprunteurTopo(User idEmprunteurTopo) {
        this.idEmprunteurTopo = idEmprunteurTopo;
    }

}
