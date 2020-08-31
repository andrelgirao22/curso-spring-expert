package com.alg.brewer.dto;

public class VendaOrigem {

	private String mes;
	private Integer totalNacional;
	private Integer totalInternacional;

	public VendaOrigem() {
	}
	
	public VendaOrigem(String mes, Integer totalNacional, Integer totalInternacional) {
		super();
		this.mes = mes;
		this.totalNacional = totalNacional;
		this.totalInternacional = totalInternacional;
	}

	public String getMes() {
		return mes;
	}

	public Integer getTotalNacional() {
		return totalNacional;
	}

	public Integer getTotalInternacional() {
		return totalInternacional;
	}
	
}
