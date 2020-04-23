package com.alg.brewer.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.repositories.CervejasRepository;

@Controller
public class CervejasController {

	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@Autowired
	private CervejasRepository repository;
	
	@RequestMapping("/cervejas/novo")
	public String novo(Cerveja cerveja) {
		
		if(logger.isDebugEnabled()) {
			logger.error("aqui é um logger de erro");			
		}
		
		repository.findAll();
		
		return "cerveja/CadastroCerveja";
	}

	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");
		
		return "redirect:/cervejas/novo";
	}
	
}
