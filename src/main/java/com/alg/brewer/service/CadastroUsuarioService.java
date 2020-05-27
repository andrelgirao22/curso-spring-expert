package com.alg.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.UsuariosRepository;
import com.alg.brewer.service.exception.UsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuariosRepository repository;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExist = this.repository.findByEmail(usuario.getEmail());
		
		if(usuarioExist.isPresent()) {
			throw new UsuarioException("Email já cadastrado.");
		}
		
		this.repository.save(usuario);
		
	}
	
}
