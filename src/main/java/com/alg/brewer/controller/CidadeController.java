package com.alg.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alg.brewer.model.Cidade;
import com.alg.brewer.model.Usuario;
import com.alg.brewer.repositories.CidadesRepository;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadesRepository cidadesRepository;
	
	@RequestMapping("/novo")
	public String novo(Usuario usuario) {
		return "cidade/CadastroCidade";
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		return this.cidadesRepository.findByEstadoCodigo(codigoEstado);
	}
	
}
