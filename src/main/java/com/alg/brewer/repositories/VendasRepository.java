package com.alg.brewer.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.filter.VendaFilter;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Long>  {

	Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

	
	
}
