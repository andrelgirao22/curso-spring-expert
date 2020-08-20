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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.alg.brewer.validation.SKU;

@Entity
@Table(name= "cerveja")
public class Cerveja implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@SKU
	@NotBlank(message = "Sku é obrigatório")
	private String sku;

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	@NotBlank(message = "A descrição é obrigatória")
	@Size(min = 1, max = 50)
	private String descricao;
	
	@Column(name= "valor")
	@NotNull(message = "O valor é obrigatório")
	@DecimalMin("0.01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que R$ 9999999,99")
	private BigDecimal valor;
	
	@NotNull(message = "O teor alcóolico é obrigatório")
	@DecimalMax(value = "100.0", message = "O valor do Teor Alcólico não pode ser superior a R$ 100,00")
	@Column(name= "teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@NotNull(message = "A quantidade de estoque é obrigatório")
	@Column(name= "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message = "A Comissão é obrigatória")
	@DecimalMax(value = "100.0", message = "Comissão não pode ser superior a R$100,00")
	@Column(name= "comissao")
	private BigDecimal comissao;
	
	@NotNull(message = "Origem é obrigatória")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message = "Sabor é obrigatório")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull(message = "O estilo é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_estilo")
	private Estilo estilo;
	
	private String foto;

	@Column(name = "content_type")
	private String contentType;
	
	@PrePersist @PreUpdate
	private void prePersiste() {
		sku = sku.toUpperCase();
	}
	
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
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public String getFoto() {
		return foto;
	}
	
	public String getFotoOuMock() {
		return foto.isEmpty() ? "cerveja-mock.png" : foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
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
