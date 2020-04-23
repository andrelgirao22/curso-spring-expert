package com.alg.brewer.model;

public enum Sabor {

	ADOCICADA("Adocicada"), 
	AMARGA("Amarga"),
	FORTE("Forte"),
	FRUTADA("Frutada"),
	SUAVE("Suave");
	
	private String descricao;
	
	public String getDescricao() {
		return this.descricao;
	}
	
	Sabor(String descricao) {
		this.descricao = descricao;
	}
	
}
