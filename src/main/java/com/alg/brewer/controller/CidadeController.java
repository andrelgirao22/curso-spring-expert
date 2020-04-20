package com.alg.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alg.brewer.model.Usuario;

@Controller
public class CidadeController {

	@RequestMapping("/cidades/novo")
	public String novo(Usuario usuario) {
		return "cidade/CadastroCidade";
	}
	
}
