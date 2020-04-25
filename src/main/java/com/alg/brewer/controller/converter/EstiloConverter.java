package com.alg.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;

import com.alg.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String codigo) {

		if(codigo.isEmpty()) return null;
		
		Estilo estilo = new Estilo();
		estilo.setId(Long.valueOf(codigo));
		return estilo;
	}

}
