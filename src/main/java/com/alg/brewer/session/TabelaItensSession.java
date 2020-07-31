package com.alg.brewer.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItensSession {

	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
		TabelaItensVenda tabelaItensVenda = getTabelaItensVenda(uuid);
		
		tabelaItensVenda.adicionarItem(cerveja, quantidade);
		tabelas.add(tabelaItensVenda);
	}


	public void alterarQuantidade(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItensVenda tabelaItensVenda = getTabelaItensVenda(uuid);
		tabelaItensVenda.alterarQuantidade(cerveja, quantidade);
	}

	public void excluirItem(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabelaItensVenda = getTabelaItensVenda(uuid);
		tabelaItensVenda.excluirItem(cerveja);
	}

	public List<ItemVenda> getItens(String uuid) {
		return getTabelaItensVenda(uuid).getItens();
	}
	
	private TabelaItensVenda getTabelaItensVenda(String uuid) {
		TabelaItensVenda tabelaItensVenda = this.tabelas.stream()
				.filter(i -> i.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaItensVenda(uuid));
		return tabelaItensVenda;
	}
	
}
