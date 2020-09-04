package com.alg.brewer.storage.s3;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alg.brewer.storage.FotoStorage;

@Profile("prod")
@Component
public class FotoStorageS3 implements FotoStorage {
	
	@Override
	public String salvar(MultipartFile[] foto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public byte[] recuperarFoto(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] recuperarThumbnail(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(String foto) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getUrl(String foto) {
		return null;
	}
}
