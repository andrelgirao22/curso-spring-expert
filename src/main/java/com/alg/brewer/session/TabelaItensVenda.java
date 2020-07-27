package com.alg.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setCerveja(cerveja);
		itemVenda.setQuantidade(quantidade);
		itemVenda.setValorUnitario(cerveja.getValor());
		
		this.itens.add(itemVenda);
	}


	public int total() {
		return this.itens.size();
	}
}
