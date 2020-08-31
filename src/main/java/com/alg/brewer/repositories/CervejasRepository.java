package com.alg.brewer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.helper.CervejasQueries;

@Repository
public interface CervejasRepository extends JpaRepository<Cerveja, Long> , CervejasQueries {

	public Optional<Cerveja> findBySkuIgnoreCase(String sku);

	@Query("SELECT SUM(c.quantidadeEstoque) FROM Cerveja c")
	public Integer sumAmountByQuantidadeEstoque();
	
	@Query("SELECT SUM(c.quantidadeEstoque*c.valor) FROM Cerveja c")
	public Integer sumAmountByValorQuantidadeEstoque();
	
}
