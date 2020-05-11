package com.alg.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.model.Estilo;
import com.alg.brewer.repositories.EstilosRepository;
import com.alg.brewer.repositories.filter.EstiloFilter;
import com.alg.brewer.service.CadastroEstiloService;
import com.alg.brewer.service.exception.EstiloException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

	@Autowired
	private CadastroEstiloService service;
	
	@Autowired
	private EstilosRepository repository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView modelAndView = new ModelAndView("estilo/CadastroEstilo");
		return modelAndView;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(estilo);
		}
		
		try {
			this.service.salvar(estilo);
		} catch(EstiloException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
			
		this.service.salvar(estilo);
		return ResponseEntity.ok(estilo);
	}
	
	@GetMapping
	public ModelAndView pesquisa(EstiloFilter estiloFilter, 
			BindingResult result, @PageableDefault(size = 2) Pageable pageable,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("estilo/PesquisaEstilos");
		
		PageWrapper<Estilo> pagina = 
				new PageWrapper<Estilo>(this.repository.filtrar(estiloFilter, pageable), request);
		
		modelAndView.addObject("pagina", pagina);
		
		return modelAndView;
	}
	
}
