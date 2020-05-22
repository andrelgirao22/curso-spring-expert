package com.alg.brewer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Cliente;
import com.alg.brewer.model.Estado;
import com.alg.brewer.repositories.ClienteRepository;
import com.alg.brewer.repositories.EstadoRepository;
import com.alg.brewer.repositories.filter.ClienteFilter;
import com.alg.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

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
		
		Optional<Cliente> isClienteExist = this.repository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if(isClienteExist.isPresent()) {
			throw new CpfCnpjClienteJaCadastradoException("Cpf/Cpnj já cadastrado");
		}
		
		this.repository.save(cliente);
	}

	public Page<Cliente> filtrar(ClienteFilter cervejaFilter, Pageable pageable) {
		return repository.filtrar(cervejaFilter, pageable);
	}
	
}
