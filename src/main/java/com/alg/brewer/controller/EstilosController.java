package com.alg.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alg.brewer.model.Cliente;

@Controller
public class EstilosController {

	@RequestMapping("/estilos/novo")
	public String novo(Cliente cliente) {
		return "estilo/CadastroEstilo";
	}
	
}
