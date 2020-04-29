package com.alg.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.EstilosRepository;
import com.alg.brewer.service.exception.EstiloException;

@Service
public class CadastroEstiloService {

	@Autowired
	private EstilosRepository repository;
	
	
	@Transactional
	public void salvar(Estilo estilo) {
		
		Optional<Estilo> optionalEstilo = repository.findByNomeIgnoreCase(estilo.getNome());
		
		
		if(optionalEstilo.isPresent()) {
			throw new EstiloException("Nome do estilo já cadastrado");
		}
		
		this.repository.saveAndFlush(estilo);
	}
	
}
