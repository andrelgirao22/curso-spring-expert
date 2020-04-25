package com.alg.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.model.Estilo;
import com.alg.brewer.service.CadastroEstiloService;

@Controller
public class EstilosController {

	@Autowired
	private CadastroEstiloService service;
	
	@RequestMapping("/estilos/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView modelAndView = new ModelAndView("estilo/CadastroEstilo");
		return modelAndView;
	}
	
	@RequestMapping(value = "/estilos/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		this.service.salvar(estilo);
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		
		return new ModelAndView("redirect:/estilos/novo");
	}
	
}
