package com.alg.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	@GetMapping("novo")
	public ModelAndView vendas() {
		
		ModelAndView mav = new ModelAndView("venda/CadastroVenda");
		
		return mav;
	}
	
}
