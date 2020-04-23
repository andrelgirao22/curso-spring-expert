package com.alg.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name= "cerveja")
public class Cerveja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Sku é obrigatório")
	private String sku;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@Size(min = 1, max = 50)
	private String descricao;
	
	@Column(name= "valor")
	private BigDecimal valor;
	
	@Column(name= "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@Column(name= "comissao")
	private BigDecimal comissao;
	
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@ManyToOne
	@JoinColumn(name = "id_estilo")
	private Estilo estilo;
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}
	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}
	public BigDecimal getComissao() {
		return comissao;
	}
	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	public Origem getOrigem() {
		return origem;
	}
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	public Sabor getSabor() {
		return sabor;
	}
	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}
	public Estilo getEstilo() {
		return estilo;
	}
	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
