package com.alg.brewer.venda;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class TabelaItensVendaTest {

	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setup() {
		
		this.tabelaItensVenda = new TabelaItensVenda();
		
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
		
	}
	
}
