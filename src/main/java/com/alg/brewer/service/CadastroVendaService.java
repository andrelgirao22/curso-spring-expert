package com.alg.brewer.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.ItemVenda;
import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.VendasRepository;

@Service
public class CadastroVendaService {

	@Autowired
	private VendasRepository repository;
	
	@Transactional
	public void salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCricao(LocalDateTime.now());
		}
		
		BigDecimal valorTotalItens = venda.getItens().stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.get();
		
		BigDecimal valorTotal = calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto());
		venda.setValorTotal(valorTotal);
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega()));
		}
		
		this.repository.save(venda);
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrente, BigDecimal valorDesconto) {
		BigDecimal valorTotal = valorTotalItens
				.add(Optional.ofNullable(valorFrente).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
	}
	
}
