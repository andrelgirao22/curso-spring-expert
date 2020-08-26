package com.alg.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.model.Cliente;
import com.alg.brewer.model.TipoPessoa;
import com.alg.brewer.repositories.ClienteRepository;
import com.alg.brewer.repositories.filter.ClienteFilter;
import com.alg.brewer.service.CadastroClienteService;
import com.alg.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import com.alg.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private CadastroClienteService clienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping(value = {"/novo"})
	public ModelAndView novo(Cliente cliente) {
		
		ModelAndView modelAndView = new ModelAndView("cliente/CadastroCliente");
		
		modelAndView.addObject("tiposPessoa", TipoPessoa.values());
		modelAndView.addObject("estados", clienteService.listarEstados());
		
		return modelAndView;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cliente cliente) {
		clienteService.buscarComEndereco(cliente);
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
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
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return this.clienteRepository.findByNomeStartingWith(nome);
	}

	private void validarTamanhoNome(String nome) {
		
		if(StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente) {
		try {
			clienteService.excluir(cliente);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
