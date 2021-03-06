package com.alg.brewer.session;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.session.TabelaItensVenda;

public class TabelaItensVendaTest {

	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setup() {
		this.tabelaItensVenda = new TabelaItensVenda("1");
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
	
		this.tabelaItensVenda.adicionarItem(cerveja,1);
		
		assertEquals(valor, tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		
		Cerveja c1 = new Cerveja();
		c1.setId(1l);
		BigDecimal v1 = new BigDecimal("8.90");
		c1.setValor(v1);
		
		Cerveja c2 = new Cerveja();
		c2.setId(2L);
		BigDecimal v2 = new BigDecimal("4.99");
		c2.setValor(v2);
	
		this.tabelaItensVenda.adicionarItem(c1,1);
		this.tabelaItensVenda.adicionarItem(c2,2);
		
		assertEquals(new BigDecimal("18.88"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosItens() throws Exception {
		
		Cerveja c1 = new Cerveja();
		c1.setId(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		this.tabelaItensVenda.adicionarItem(c1,1);
		this.tabelaItensVenda.adicionarItem(c1,1);
		
		assertEquals(1, tabelaItensVenda.getItens().size());
		
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem( ) throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setId(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		this.tabelaItensVenda.adicionarItem(c1,1);
		this.tabelaItensVenda.alterarQuantidade(c1, 3);
		
		assertEquals(new BigDecimal("13.50"), tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveExluirItem() throws Exception {
		Cerveja c1 = new Cerveja();
		c1.setId(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		Cerveja c2 = new Cerveja();
		c2.setId(2L);
		c2.setValor(new BigDecimal("8.50"));
		
		Cerveja c3 = new Cerveja();
		c3.setId(3L);
		c3.setValor(new BigDecimal("5.50"));
		
		this.tabelaItensVenda.adicionarItem(c1,1);
		this.tabelaItensVenda.adicionarItem(c2, 3);
		this.tabelaItensVenda.adicionarItem(c3, 5);
		
		this.tabelaItensVenda.excluirItem(c3);
		
		assertEquals(2, tabelaItensVenda.total());
		assertEquals(new BigDecimal("30.00"), tabelaItensVenda.getValorTotal());
	}
	
}
