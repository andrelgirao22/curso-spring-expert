package com.alg.brewer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cliente;
import com.alg.brewer.model.Estado;
import com.alg.brewer.repositories.ClienteRepository;
import com.alg.brewer.repositories.EstadoRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository repository;
	
	public List<Estado> listarEstados() {
		return this.estadoRepository.findAll();
	}
	
	public List<Cliente> listar() {
		return this.repository.findAll();
	}
	
	@Transactional
	public void salvar(Cliente cliente) {
		
		
		/*cliente = 
		
		if(optionalEstilo.isPresent()) {
			throw new EstiloException("Nome do estilo já cadastrado");
		}
		
		this.repository.saveAndFlush(estilo);*/
	}
	
}
