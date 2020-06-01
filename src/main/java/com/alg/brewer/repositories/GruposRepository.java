package com.alg.brewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alg.brewer.model.Grupo;

public interface GruposRepository extends JpaRepository<Grupo, Long> {
	
}
