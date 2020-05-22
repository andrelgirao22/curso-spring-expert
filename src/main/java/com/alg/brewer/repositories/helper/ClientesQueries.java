package com.alg.brewer.repositories.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Cliente;
import com.alg.brewer.repositories.filter.ClienteFilter;

public interface ClientesQueries {

	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);
}

