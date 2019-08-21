package fr.rbo.model;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank (message = "ERREUR : Le commentaire ne doit pas être vide.")
    @Size (max = 120, message = "ERREUR : Le commentaire ne doit pas faire plus de 120 caractères")
    @Column(nullable = false, length = 120)
    private String message;

    @Column(name = "dateDeMiseAJour", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateDeMiseAJour;

    @ManyToOne
    private User userCommentaire;

    @ManyToOne
    private Spot spot;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateDeMiseAJour() {
        return dateDeMiseAJour;
    }

    public void setDateDeMiseAJour(Date dateDeMiseAJour) {
        this.dateDeMiseAJour = dateDeMiseAJour;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public User getUserCommentaire() {
        return userCommentaire;
    }

    public void setUserCommentaire(User userCommentaire) {
        this.userCommentaire = userCommentaire;
    }
}