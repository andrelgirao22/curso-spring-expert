package com.alg.brewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.model.Estado;

@Repository
public interface CidadesRepository extends JpaRepository<Cidade, Long> {

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByCodigo(Long codigo);

	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
	
}
