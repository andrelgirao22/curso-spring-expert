package com.alg.brewer.storage;

import org.springframework.web.multipart.MultipartFile;


public interface FotoStorage {

	String salvandoTemporariamente(MultipartFile [] file);

	byte[] recuperarFotoTemporaria(String nome);

	void salvar(String foto);

	byte[] recuperarFoto(String nome);
	
}
