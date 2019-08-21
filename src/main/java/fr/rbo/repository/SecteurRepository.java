package fr.rbo.repository;

import fr.rbo.model.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

    Secteur findById(int id);

}
