package fr.rbo.service;

import fr.rbo.model.*;

import java.util.Collection;
import java.util.List;

/**
 * CRUD des topos
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
    public List<Topo> listeTopos(Topo topoCherche);

    /**
     * demande emprunt d'une topo
     * @param topoId ID de la topo demandée pour emprunt
     * @param utilisateur qui demande l'emprunt
     * @return resultat true = OK / false = KO
     */
    public Boolean emprunterTopo(Long topoId, User utilisateur);

    /**
     * accepter la demande d'emprunt
     * @param topoId ID de la topo demandée pour emprunt
     * @return resultat true = OK / false = KO
     */
    public Boolean accepterEmpruntTopo(Long topoId);

    /**
     * refuser la demande d'emprunt (permet aussi de réinitialiser lors d'un retour d'une topo)
     * @param topoId ID de la topo demandée pour emprunt
     * @return resultat true = OK / false = KO
     */
    public Boolean annulerDemandeTopo(Long topoId);

    }
