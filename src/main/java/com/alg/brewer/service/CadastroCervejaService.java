package com.alg.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.CervejasRepository;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;

	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
	}
	
}
