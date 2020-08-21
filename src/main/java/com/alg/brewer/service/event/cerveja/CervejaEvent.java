package com.alg.brewer.service.event.cerveja;

import com.alg.brewer.model.Cerveja;

public class CervejaEvent {

	private Cerveja cerveja;

	public CervejaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}
	
	public boolean hasFoto() {
		return !this.getCerveja().getFoto().isEmpty();
	}
	
	public boolean isNovaFoto() {
		return cerveja.isNovaFoto();
	}
	
}
