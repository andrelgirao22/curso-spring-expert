package com.alg.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.StatusVenda;
import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.VendasRepository;
import com.alg.brewer.repositories.filter.VendaFilter;

@Service
public class CadastroVendaService {

	@Autowired
	private VendasRepository repository;
	
	@Transactional
	public Venda salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		} else {
			Venda vendaExistente = repository.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), 
					venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
		}
		
		return this.repository.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		this.salvar(venda);
	}

	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable) {
		return repository.filtrar(vendaFilter, pageable);
	}

	public Venda buscarComItens(Long codigo) {
		return repository.buscarComItens(codigo);
	}

	
	
}
