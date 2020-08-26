package com.alg.brewer.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.repositories.CidadesRepository;
import com.alg.brewer.repositories.filter.CidadeFilter;
import com.alg.brewer.service.exception.CidadeException;
import com.alg.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadesRepository repository;
	
	@Transactional
	public void salvar(Cidade cidade) {
		
		Optional<Cidade> cidadeExist = this.repository.findByNomeAndEstado(cidade.getNome(),cidade.getEstado());
		
		if(cidadeExist.isPresent()) {
			throw new CidadeException("Cidade já cadastrada.");
		}
		
		this.repository.save(cidade);
		
	}
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado) {
		return repository.findByEstadoCodigo(codigoEstado);
	}

	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		return repository.filtrar(cidadeFilter, pageable);
	}

	public Cidade buscarComEstado(Long codigo) {
		return repository.buscarComEstado(codigo);
	}

	@Transactional
	public void excluir(Cidade cidade) {
		try {			
			repository.delete(cidade);
			repository.flush();
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cidade. O registro está sendo usado.");
		}
	}
	
}
