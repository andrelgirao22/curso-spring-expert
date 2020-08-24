package com.alg.brewer.repositories.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable);
	
	public Venda buscarComItens(Long id);
	
}
