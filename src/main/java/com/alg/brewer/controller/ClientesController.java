package com.alg.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.model.Cliente;
import com.alg.brewer.model.TipoPessoa;
import com.alg.brewer.repositories.ClienteRepository;
import com.alg.brewer.repositories.filter.ClienteFilter;
import com.alg.brewer.service.CadastroClienteService;
import com.alg.brewer.service.exception.CpfCnpjClienteJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private CadastroClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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
		try {			
			this.clienteService.salvar(cliente);
		} catch (CpfCnpjClienteJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		attr.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter cervejaFilter, BindingResult result, 
			@PageableDefault(size=2) Pageable pageable, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("cliente/PesquisaClientes");
		
		modelAndView.addObject("tiposPessoa", TipoPessoa.values());
		
		PageWrapper<Cliente> paginaWrapper = new PageWrapper<>(clienteRepository.filtrar(cervejaFilter, pageable), request);
		modelAndView.addObject("pagina", paginaWrapper);
		
		
		return modelAndView;
	}
	
}
