package com.alg.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	String salvar(MultipartFile[] foto);

	byte[] recuperarFoto(String nome);

	byte[] recuperarThumbnail(String nome);

	void excluir(String foto);

	String getUrl(String foto);
	
}
