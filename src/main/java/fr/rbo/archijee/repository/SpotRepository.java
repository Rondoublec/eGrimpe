package fr.rbo.archijee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.rbo.archijee.model.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long>{}
