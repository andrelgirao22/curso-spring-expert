package com.alg.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.alg.brewer.dto.FotoDTO;
import com.alg.brewer.storage.FotoStorage;
import com.alg.brewer.storage.FotosStorageRunnable;

@RestController
@RequestMapping(value = "/fotos")
public class FotosController {
	
	@Autowired
	private FotoStorage fotoStorage;

	@PostMapping
	public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<FotoDTO> result = new DeferredResult<FotoDTO>();

		Thread runnable = new Thread(new FotosStorageRunnable(files, result, fotoStorage));
		runnable.start();
		
		return result;
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperaFoto(@PathVariable String nome) {
		return fotoStorage.recuperarFoto(nome);
	}
	
}
