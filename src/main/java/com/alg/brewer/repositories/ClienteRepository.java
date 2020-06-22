package com.alg.brewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Cliente;
import com.alg.brewer.repositories.helper.ClientesQueries;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> , ClientesQueries {

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

	public List<Cliente> findByNomeStartingWith(String nome);

}
