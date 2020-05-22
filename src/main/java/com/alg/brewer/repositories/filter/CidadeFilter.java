package com.alg.brewer.repositories.filter;

import com.alg.brewer.model.Estado;

public class CidadeFilter {

	private Estado estado;
	
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
		
}
