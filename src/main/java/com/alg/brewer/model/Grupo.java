package com.alg.brewer.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "grupo")
public class Grupo {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@ManyToMany
	@NotNull(message = "Selecione pelo meno um grupo")
	@JoinTable(name="grupo_pemissao" , 
		joinColumns = @JoinColumn(name = "codigo_grupo"), 
		inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
	private List<Permissao> permissoes;
	
	
}
