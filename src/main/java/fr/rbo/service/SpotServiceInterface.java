package fr.rbo.service;

import fr.rbo.model.*;

import java.util.Collection;
import java.util.List;

/**
 * CRUD des spots
 */
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
    public void modifCommentaire(Commentaire commentaire, Long spotId, String email);

    /**
     * supprimer le commentaire
     * @param idCommentaire
     * @param spotId
     */
    public void supprCommentaire(int idCommentaire, Long spotId);

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
     * supprimer le voie
     * @param idVoie
     * @param idSecteur
     */
    public void supprVoie(int idVoie, int idSecteur);

    /**
     * ramener une voie par id
     * @param idVoie
     * @return Voie
     */
    public Voie getVoie(int idVoie);

    /**
     * ajouter une longueur à une voie
     * @param idVoie
     * @param Longueur
     * @return
     */
    public Voie ajoutLongueur(int idVoie, Longueur Longueur);

    /**
     * supprimer le voie
     * @param idLongueur
     * @param idVoie
     */
    public void supprLongueur(int idLongueur, int idVoie);

    /**
     * ramener une voie par id
     * @param idLongueur
     * @return Longueur
     */
    public Longueur getLongueur(int idLongueur);

    /**
     * recherche spot selon critères
     * @param spotCherche objet qui contient les valeurs des critères de recherche
     * @return Liste des spots correspondant aux critères
     */
    public List<Spot> recupSpots(Spot spotCherche);

}
