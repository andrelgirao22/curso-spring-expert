package com.alg.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.service.event.cerveja.CervejaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	

	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
		
		this.publisher.publishEvent(new CervejaEvent(cerveja));
	}
	
}
