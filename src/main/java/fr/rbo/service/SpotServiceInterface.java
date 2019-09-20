package fr.rbo.service;

import fr.rbo.model.Commentaire;
import fr.rbo.model.Secteur;
import fr.rbo.model.Spot;
import fr.rbo.model.Voie;

import java.util.Collection;

public interface SpotServiceInterface {

    public Spot saveSpot(Spot spot);
    public Boolean deleteSpot(Long spotId);
    public Spot editSpot(Spot spot);
    public Spot findSpot(Long spotId);
    public Collection<Spot> getAllSpots();

    /**
     * ajouter un commentaire à un site
     * @param commentaire
     * @param spotId
     * @param email
     */
    public void ajoutCommentaire(Commentaire commentaire, Long spotId, String email);

    /**
     * modifier le message du commentaire
     * @param commentaire (message modifié)
     * @param spotId
     * @param email
     */
    void modifCommentaire(Commentaire commentaire, Long spotId, String email);

    /**
     * supprimer le commentaire
     * @param idCommentaire
     * @param spotId
     */
    void supprCommentaire(int idCommentaire, Long spotId);

    /**
     * Ajouter un secteur à un site
     * @param spotId
     * @param secteur
     * @return
     */
    public Spot ajoutSecteur(Long spotId, Secteur secteur);

    /**
     * supprimer le secteur
     * @param idSecteur
     * @param spotId
     */
    void supprSecteur(int idSecteur, Long spotId);

    /**
     * chercher le secteur par id
     * @param idSecteur
     * @return Secteur
     */
    public Secteur getSecteur(int idSecteur);

    /**
     * ajouter une voie à un secteur
     * @param idSecteur
     * @param voie
     * @return
     */
    public Secteur ajoutVoie(int idSecteur, Voie voie);

    /**
     * ramener une voie par id
     * @param idVoie
     * @return Voie
     */
    public Voie getVoie(int idVoie);

}
