package com.alg.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItensVenda {

	private List<ItemVenda> itens = new ArrayList<ItemVenda>();
	
	public BigDecimal getValorTotal() {
		return this.itens.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		
		Optional<ItemVenda> itemVendaOptional = buscarItemPorCerveja(cerveja);
		
		ItemVenda itemVenda = null;
		if(itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		} else {
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());
			
			this.itens.add(0,itemVenda);
		}
		
	}

	public void alterarQuantidade(Cerveja cerveja, Integer quantidade) {
		ItemVenda itemVenda = this.buscarItemPorCerveja(cerveja).get();
		itemVenda.setQuantidade(quantidade);
	}

	public int total() {
		return this.itens.size();
	}

	public List<ItemVenda> getItens() {
		return this.itens;
	}
	
	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		Optional<ItemVenda> itemVendaOptional = 
				this.itens.stream()
				.filter(i -> i.getCerveja().equals(cerveja))
				.findAny();
		return itemVendaOptional;
	}
}
