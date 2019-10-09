package fr.rbo.service;

import fr.rbo.model.*;

import java.util.Collection;
import java.util.List;

/**
 * Documenter en premier les classes puis ensuite faire les méthode "non évidentes, ne pas documenter les get / set)
 */
public interface TopoServiceInterface {

    public Topo saveTopo(Topo topo, User utilisateur);
    public Boolean deleteTopo(Long topoId);
    public Topo editTopo(Topo topo, User utilisateur);
    public Topo findTopo(Long topoId);
    public Collection<Topo> getAllTopos();

    /**
     * recherche topo selon critères
     * @param topoCherche objet qui contient les valeurs des critères de recherche
     * @return Liste des topos correspondant aux critères
     */
    public List<Topo> chercheTopos(Topo topoCherche);

}
