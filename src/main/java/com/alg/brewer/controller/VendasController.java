package com.alg.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("novo")
	public ModelAndView vendas() {
		
		ModelAndView mav = new ModelAndView("venda/CadastroVenda");
		
		return mav;
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {
		Cerveja cerveja = this.cervejasRepository.findOne(codigoCerveja);
		
		this.tabelaItensVenda.adicionarItem(cerveja, 1);
		System.out.println(">>>> total de itens " + this.tabelaItensVenda.total());
		
		ModelAndView modelAndView = new ModelAndView("venda/TabelaItensVenda");
		modelAndView.addObject("itens", this.tabelaItensVenda.getItens());
		
		return modelAndView;
		
	}
	
}
