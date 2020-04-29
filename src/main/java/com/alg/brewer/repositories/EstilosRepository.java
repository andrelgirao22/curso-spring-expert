package com.alg.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Estilo;

@Repository
public interface EstilosRepository extends JpaRepository<Estilo, Long>{

	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
	
}
