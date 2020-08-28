package com.alg.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alg.brewer.repositories.VendasRepository;

@Controller
public class DashboardController {

	@Autowired
	private VendasRepository vendas;
	
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		mv.addObject("vendasNoAno", vendas.valorTotalAno());
		mv.addObject("vendasNoMes", vendas.valorTotalMes());
		mv.addObject("ticketMedio", vendas.valorTicketMedioAno());
		return mv;
	}
	
}
