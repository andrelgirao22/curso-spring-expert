package com.alg.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.ItemVenda;
import com.alg.brewer.repositories.CervejasRepository;

@Component
public class VendaListener {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@EventListener
	public void vendaEmitida(VendaEvent event) {
		
		for(ItemVenda item : event.getVenda().getItens()) {
			Cerveja cerveja = cervejasRepository.findOne(item.getCerveja().getId());
			cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
			cervejasRepository.save(cerveja);
		}
		
	}
	
}
