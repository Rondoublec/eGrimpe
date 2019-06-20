package fr.rbo.archijee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.rbo.archijee.model.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
	public Genre findByCode(String code);
}
