package fr.rbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.rbo.model.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long>{}
