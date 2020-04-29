package com.alg.brewer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alg.brewer.service.CadastroCervejaService;
import com.alg.brewer.storage.FotoStorage;
import com.alg.brewer.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorageLocal() {
		return new FotoStorageLocal();
	}
	
}
