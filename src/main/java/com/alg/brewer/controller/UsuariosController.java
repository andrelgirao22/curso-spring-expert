package com.alg.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.codehaus.groovy.classgen.asm.MopWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.GruposRepository;
import com.alg.brewer.repositories.filter.UsuarioFilter;
import com.alg.brewer.service.CadastroUsuarioService;
import com.alg.brewer.service.StatusUsuario;
import com.alg.brewer.service.exception.SenhaObrigatoriaUsuarioException;
import com.alg.brewer.service.exception.UsuarioException;

@RequestMapping(value = "/usuarios")
@Controller
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService service;
	
	@Autowired
	private GruposRepository gruposRepository;

	@GetMapping(value = "/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
		modelAndView.addObject("grupos", gruposRepository.findAll());
		
		return modelAndView;
	}
	
	@PostMapping({ "/novo", "{\\+d}" })
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			this.service.salvar(usuario);
		} catch(UsuarioException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch(SenhaObrigatoriaUsuarioException se) {
			result.rejectValue("senha", se.getMessage(), se.getMessage());
			return novo(usuario);
		}
		attr.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso");
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result, 
			@PageableDefault(size = 3) Pageable pageable, HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("usuario/PesquisaUsuarios");
		modelAndView.addObject("grupos", gruposRepository.findAll());
	
		PageWrapper<Usuario> pagina = 
			new PageWrapper<>(this.service.filtrar(usuarioFilter, pageable), request);
	
		modelAndView.addObject("pagina", pagina);
		
		return modelAndView;
	}
	
	@PutMapping("/status")
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long []codigos, @RequestParam("status") StatusUsuario status) {
		service.alterarStatus(codigos, status);
		
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		Usuario usuario = this.service.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
}
