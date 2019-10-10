package fr.rbo.repository;

import fr.rbo.model.Spot;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpotRepositoryImpl implements SpotRepositoryInterface {

    final EntityManager em;

    public SpotRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Spot> rechercheSpot(Spot spotCherche) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Spot> cq = cb.createQuery(Spot.class);

        Root<Spot> spot = cq.from(Spot.class);
        List<Predicate> predicates = new ArrayList<>();

        if (spotCherche.getNomSpot() != null) {
            if (!spotCherche.getNomSpot().equals("")) {
                predicates.add(cb.like(spot.get("nomSpot"), "%" + spotCherche.getNomSpot() + "%"));
            }
            if (!spotCherche.getCodePostalSpot().equals("")) {
                predicates.add(cb.like(spot.get("codePostalSpot"), "%" + spotCherche.getCodePostalSpot() + "%"));
            }
            if (!spotCherche.getCommuneSpot().equals("")) {
                predicates.add(cb.like(spot.get("communeSpot"), "%" + spotCherche.getCommuneSpot() + "%"));
            }
            if (spotCherche.isLabelAmi()) {
                predicates.add(cb.equal(spot.get("labelAmi"), spotCherche.isLabelAmi()));
            }
        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        return em.createQuery(cq).getResultList();
    }
}
