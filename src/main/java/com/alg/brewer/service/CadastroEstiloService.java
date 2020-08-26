package com.alg.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.EstilosRepository;
import com.alg.brewer.service.exception.EstiloException;
import com.alg.brewer.service.exception.ImpossivelExcluirEntidadeException;

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


	@Transactional
	public void excluir(Estilo estilo) {
		try {			
			repository.delete(estilo);
			repository.flush();
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar estilo. O registro está sendo usado.");
		}
		
	}
	
}
