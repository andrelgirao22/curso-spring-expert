package com.alg.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.dto.CervejaDTO;
import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.Origem;
import com.alg.brewer.model.Sabor;
import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.repositories.EstilosRepository;
import com.alg.brewer.repositories.filter.CervejaFilter;
import com.alg.brewer.service.CadastroCervejaService;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

	@Autowired
	private EstilosRepository estilosRepository;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private CervejasRepository cervejasRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		
		ModelAndView modelAndView = new ModelAndView("cerveja/CadastroCerveja");
		modelAndView.addObject("sabores", Sabor.values());
		modelAndView.addObject("estilos", estilosRepository.findAll());
		modelAndView.addObject("origens", Origem.values());
		
		return modelAndView;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cerveja);
		}
		
		this.cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");
		
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, 
			@PageableDefault(size=2) Pageable pageable, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("cerveja/PesquisaCervejas");
		
		modelAndView.addObject("origens", Origem.values());
		modelAndView.addObject("sabores", Sabor.values());
		modelAndView.addObject("estilos", estilosRepository.findAll());
		
		PageWrapper<Cerveja> paginaWrapper = new PageWrapper<Cerveja>(cervejasRepository.filtrar(cervejaFilter, pageable), request);
		modelAndView.addObject("pagina", paginaWrapper);
		
		
		return modelAndView;
	}
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> filtrar(String skuOuNome) {
		return cervejasRepository.porSkuOuNome(skuOuNome);
	}
	
}
