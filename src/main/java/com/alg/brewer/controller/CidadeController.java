package com.alg.brewer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.repositories.EstadoRepository;
import com.alg.brewer.service.CadastroCidadeService;
import com.alg.brewer.service.exception.CidadeException;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CadastroCidadeService cidadesService;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		
		ModelAndView modelAndView = new ModelAndView("cidade/CadastroCidade");
		
		modelAndView.addObject("estados", this.estadoRepository.findAll());
		
		return modelAndView;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		return this.cidadesService.findByEstadoCodigo(codigoEstado);
	}
	
	@PostMapping(value = "/novo")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(cidade);
		}
		
		try {
			this.cidadesService.salvar(cidade);
		} catch(CidadeException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		attributes.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso");
		
		return new ModelAndView("redirect:/cidades/novo");
	}
	
}
