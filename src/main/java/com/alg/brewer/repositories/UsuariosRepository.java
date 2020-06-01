package com.alg.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.helper.UsuariosQueries;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long> , UsuariosQueries {

	Optional<Usuario> findByEmail(String email);

}
