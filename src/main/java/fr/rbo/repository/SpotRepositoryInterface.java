package fr.rbo.repository;

import fr.rbo.model.Spot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepositoryInterface {

    List<Spot> rechercheSpotMultiCriteres(Spot spotCherche);

}
