package com.alg.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alg.brewer.service.exception.EstiloException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(EstiloException.class)
	public ResponseEntity<String> handleNomeJaCadastradoException(EstiloException e) {
		return ResponseEntity.badRequest().body(e.getMessage());

	}
	
}
