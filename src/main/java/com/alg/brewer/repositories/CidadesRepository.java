package com.alg.brewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.model.Estado;
import com.alg.brewer.repositories.helper.CidadesQueries;

@Repository
public interface CidadesRepository extends JpaRepository<Cidade, Long> , CidadesQueries{

	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
	public Optional<Cidade> findByCodigo(Long codigo);

	public Optional<Cidade> findByNomeAndEstado(String nome, Estado estado);
	
	@Query("select c from Cidade c join fetch c.estado where c.codigo = :codigo")
	public Cidade findByCodigoFetchingEstado(@Param("codigo") Long codigo);
	
}
