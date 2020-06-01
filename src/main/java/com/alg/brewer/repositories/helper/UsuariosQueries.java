package com.alg.brewer.repositories.helper;

import java.util.List;
import java.util.Optional;

import com.alg.brewer.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);

	public List<String> permissoes(Usuario usuario);
	
}