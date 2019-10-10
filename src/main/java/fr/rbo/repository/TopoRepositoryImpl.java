package fr.rbo.repository;

import fr.rbo.model.Topo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TopoRepositoryImpl implements TopoRepositoryInterface {

    final EntityManager em;

    public TopoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Topo> rechercheTopo(Topo topoCherche) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Topo> cq = cb.createQuery(Topo.class);

        Root<Topo> topo = cq.from(Topo.class);
        List<Predicate> predicates = new ArrayList<>();

        if (topoCherche.getNomTopo() != null){
            if (!topoCherche.getNomTopo().equals("")) {
                predicates.add(cb.like(topo.get("nomTopo"), "%" + topoCherche.getNomTopo() + "%"));
            }
            if (!topoCherche.getCodePostalTopo().equals("")) {
                predicates.add(cb.like(topo.get("codePostalTopo"), "%" + topoCherche.getCodePostalTopo() + "%"));
            }
            if (topoCherche.isDisponibiliteTopo()) {
                predicates.add(cb.equal(topo.get("disponibiliteTopo"), topoCherche.isDisponibiliteTopo()));
            }
        }

        if (topoCherche.getProprietaireTopo() != null) {
            predicates.add(cb.equal(topo.get("proprietaireTopo"), topoCherche.getProprietaireTopo()));
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return em.createQuery(cq).getResultList();
    }
}
