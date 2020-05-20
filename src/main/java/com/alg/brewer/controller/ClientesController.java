package com.alg.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.model.Cliente;
import com.alg.brewer.model.TipoPessoa;
import com.alg.brewer.service.CadastroClienteService;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private CadastroClienteService clienteService;
	
	@GetMapping(value = "/novo")
	public ModelAndView novo(Cliente cliente) {
		
		ModelAndView modelAndView = new ModelAndView("cliente/CadastroCliente");
		
		modelAndView.addObject("tiposPessoa", TipoPessoa.values());
		modelAndView.addObject("estados", clienteService.listarEstados());
		
		return modelAndView;
	}
	
	@PostMapping(value = "/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return novo(cliente);
		}
		
		attr.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
}
