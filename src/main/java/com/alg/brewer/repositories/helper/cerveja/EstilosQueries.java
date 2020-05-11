package com.alg.brewer.repositories.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.filter.EstiloFilter;

public interface EstilosQueries {

	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable);
	
}
