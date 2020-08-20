package com.alg.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.service.event.cerveja.CervejaEvent;
import com.alg.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.alg.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {

	@Autowired
	private CervejasRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private FotoStorage fotoStorage;
	

	@Transactional
	public void salvar(Cerveja cerveja) {
		repository.save(cerveja);
		
		this.publisher.publishEvent(new CervejaEvent(cerveja));
	}


	@Transactional
	public void excluir(Cerveja cerveja) {
		
		try {
			String foto = cerveja.getFoto();
			repository.delete(cerveja);
			repository.flush();
			fotoStorage.excluir(foto);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
		}
	}
	
}
