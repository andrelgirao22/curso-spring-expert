package com.alg.brewer.repositories.helper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alg.brewer.dto.VendaMes;
import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable);
	
	public Venda buscarComItens(Long id);
	
	public BigDecimal valorTotalAno();
	public BigDecimal valorTotalMes();
	public List<VendaMes> totalPorMes();
	public BigDecimal valorTicketMedioAno();
}
