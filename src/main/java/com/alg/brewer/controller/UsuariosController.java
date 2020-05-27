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

import com.alg.brewer.model.Usuario;
import com.alg.brewer.service.CadastroUsuarioService;
import com.alg.brewer.service.exception.UsuarioException;

@RequestMapping(value = "/usuarios")
@Controller
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService service;

	@GetMapping(value = "/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuario/CadastroUsuario");
		
		return modelAndView;
	}
	
	@PostMapping(value = "/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			this.service.salvar(usuario);
		} catch(UsuarioException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		attr.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso");
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
}
