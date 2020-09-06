package com.alg.brewer.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public final String THUMBNAIL_PREFIX = "thumbnail.";
	
	String salvar(MultipartFile[] foto);

	byte[] recuperarFoto(String nome);

	byte[] recuperarThumbnail(String nome);

	void excluir(String foto);

	String getUrl(String foto);
	
	default String renomearArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
}
