package com.alg.brewer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.UsuariosRepository;
import com.alg.brewer.repositories.filter.UsuarioFilter;
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
		
		if(usuarioExist.isPresent() && !usuarioExist.get().equals(usuario)) {
			throw new UsuarioException("Email já cadastrado.");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if(usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {			
			usuario.setSenha(encoder.encode(usuario.getSenha()));
		} else if(StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExist.get().getSenha());
		}
		
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if(!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExist.get().getAtivo());
		}
		
		this.repository.save(usuario);
		
	}
	
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		return repository.filtrar(usuarioFilter, pageable);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, this.repository);
	}
	
	public Usuario buscarComGrupos(Long codigo) {
		return this.repository.buscarComGrupos(codigo);
	}
	
}
