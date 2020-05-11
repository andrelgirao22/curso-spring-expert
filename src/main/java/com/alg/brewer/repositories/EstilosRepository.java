package com.alg.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.helper.cerveja.EstilosQueries;

@Repository
public interface EstilosRepository extends JpaRepository<Estilo, Long>, EstilosQueries {

	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
	
}
