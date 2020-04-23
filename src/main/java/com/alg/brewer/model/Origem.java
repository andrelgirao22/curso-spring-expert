package com.alg.brewer.model;

public enum Origem {

	NACIONAL("Nacional"),
	INTERNACIONAL("Internacional");
	
	private String descricao;
	
	public String getDescricao() {
		return this.descricao;
	}
	
	Origem(String descricao) {
		this.descricao = descricao;
	}
	
}
