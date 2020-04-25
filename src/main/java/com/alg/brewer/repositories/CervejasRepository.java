package com.alg.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Cerveja;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long>{

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);
	
}
