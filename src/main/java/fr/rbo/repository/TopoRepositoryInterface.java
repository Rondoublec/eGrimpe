package fr.rbo.repository;

import fr.rbo.model.Topo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopoRepositoryInterface {

    List<Topo> rechercheTopo(Topo topoCherche);

}
