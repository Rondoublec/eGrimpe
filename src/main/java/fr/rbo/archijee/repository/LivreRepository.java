package fr.rbo.archijee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.rbo.archijee.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

}
