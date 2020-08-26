package com.alg.brewer.repositories.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.repositories.filter.CidadeFilter;

public interface CidadesQueries {

	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);
	
	public Cidade buscarComEstado(Long id);
}

