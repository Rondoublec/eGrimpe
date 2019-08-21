package fr.rbo.service;

import fr.rbo.model.Commentaire;
import fr.rbo.model.Spot;

import java.util.Collection;

public interface SpotServiceInterface {

    public Spot saveSpot(Spot spot);
    public Boolean deleteSpot(Long spotId);
    public Spot editSpot(Spot spot);
    public Spot findSpot(Long spotId);
    public Collection<Spot> getAllSpots();
    public void ajoutCommentaire(Commentaire commentaire, Long spotId, String email);

}
