package fr.rbo.repository;

import fr.rbo.model.Longueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long> {

    Longueur findById(int id);

}