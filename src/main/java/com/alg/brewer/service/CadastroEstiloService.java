package com.alg.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.EstilosRepository;

@Service
public class CadastroEstiloService {

	@Autowired
	private EstilosRepository repository;
	
	
	@Transactional
	public void salvar(Estilo estilo) {
		this.repository.save(estilo);
	}
	
}
