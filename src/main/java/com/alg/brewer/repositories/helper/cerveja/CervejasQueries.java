package com.alg.brewer.repositories.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
}
