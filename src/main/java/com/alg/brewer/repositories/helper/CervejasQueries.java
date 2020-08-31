package com.alg.brewer.repositories.helper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.dto.CervejaDTO;
import com.alg.brewer.dto.ValorItensEstoque;
import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.filter.CervejaFilter;

public interface CervejasQueries {

	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);

	ValorItensEstoque valorItensEstoque();
	
}
