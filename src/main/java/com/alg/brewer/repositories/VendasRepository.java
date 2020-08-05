package com.alg.brewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Venda;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Long>  {

	
	
}
