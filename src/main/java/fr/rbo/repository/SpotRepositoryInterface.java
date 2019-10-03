package fr.rbo.repository;

import fr.rbo.model.Spot;

import java.util.List;

public interface SpotRepositoryCustom {

    List<Spot> rechercheSpotMultiCriteres(Spot spotCherche);

}
