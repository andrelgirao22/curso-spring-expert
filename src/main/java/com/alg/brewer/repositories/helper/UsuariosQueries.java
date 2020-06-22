package com.alg.brewer.repositories.helper;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);

	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
	
	
}