package fr.rbo.archijee.service;

import fr.rbo.archijee.model.Spot;

import java.util.Collection;

public interface SpotServiceInterface {

    public Spot saveSpot(Spot spot);
    public Boolean deleteSpot(Long spotId);
    public Spot editSpot(Spot spot);
    public Spot findSpot(Long spotId);
    public Collection<Spot> getAllSpots();

}
