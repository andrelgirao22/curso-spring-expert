package com.alg.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.UsuariosRepository;
import com.alg.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import com.alg.brewer.service.exception.UsuarioException;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuariosRepository repository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExist = this.repository.findByEmail(usuario.getEmail());
		
		if(usuarioExist.isPresent()) {
			throw new UsuarioException("Email já cadastrado.");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if(usuario.isNovo()) {			
			usuario.setSenha(encoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}
		
		this.repository.save(usuario);
		
	}
	
}
