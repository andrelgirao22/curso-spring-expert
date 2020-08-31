package com.alg.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.repositories.ClienteRepository;
import com.alg.brewer.repositories.VendasRepository;

@Controller
public class DashboardController {

	@Autowired
	private VendasRepository vendas;
	
	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("vendasNoAno", vendas.valorTotalAno());
		mv.addObject("vendasNoMes", vendas.valorTotalMes());
		mv.addObject("ticketMedio", vendas.valorTicketMedioAno());
		mv.addObject("totalClientes", clienteRepository.count());
		
		mv.addObject("valorItensEstoque", cervejasRepository.valorItensEstoque());
		
		//mv.addObject("totalEstoque", cervejasRepository.sumAmountByQuantidadeEstoque());
		//mv.addObject("valorTotalEstoque", cervejasRepository.sumAmountByValorQuantidadeEstoque());
		return mv;
	}
	
}
