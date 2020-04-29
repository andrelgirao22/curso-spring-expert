package com.alg.brewer.storage;

import org.springframework.web.multipart.MultipartFile;


public interface FotoStorage {

	String salvandoTemporariamente(MultipartFile [] file);

	byte[] recuperarFotoTemporaria(String nome);
	
}
