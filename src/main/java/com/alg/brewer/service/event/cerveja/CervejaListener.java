package com.alg.brewer.service.event.cerveja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alg.brewer.storage.FotoStorage;

@Component
public class CervejaListener {

	@Autowired
	private FotoStorage fotoStorage;
	
	@EventListener(condition = "#event.hasFoto() and #event.novaFoto")
	public void cervejaSalva(CervejaEvent event) {
		
		System.out.println("Nova Cerveja " + event.getCerveja());
		fotoStorage.salvar(event.getCerveja().getFoto());
	}
	
}
