package com.alg.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alg.brewer.service.CadastroCervejaService;
import com.alg.brewer.storage.FotoStorage;

@Configuration
@ComponentScan(basePackageClasses = { CadastroCervejaService.class, FotoStorage.class })
public class ServiceConfig {

}
