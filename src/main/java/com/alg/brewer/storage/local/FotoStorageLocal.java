package com.alg.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alg.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class FotoStorageLocal implements FotoStorage {
	
	private static Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this.local = getDefault().getPath(System.getProperty("user.home"), ".brewerfotos");
		criarPastas();
	}
	
	public FotoStorageLocal(Path local) {
		this.local = local;
		this.criarPastas();
	}
	
	@Override
	public String salvandoTemporariamente(MultipartFile[] file) {
		
		String novoNome = null;
		
		if(file !=null && file.length > 0) {
			MultipartFile files = file[0];
			try {
				novoNome = renomearArquivo(files.getOriginalFilename());
				files.transferTo(new File(this.localTemporario.toAbsolutePath() + getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("erro salvando a foto na pasta temporaria " + e.getMessage());
			}
		}
		
		return novoNome;
		
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto temporaria");
		}
	}
	

	@Override
	public byte[] recuperarFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto");
		}
	}


	@Override
	public void salvar(String foto) {
		try {
			Files.move(this.localTemporario.resolve(foto), this.local.resolve(foto));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo foto para destino final");
		}

		try {
			Thumbnails.of(this.local.resolve(foto).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail.");
		}
		
	}


	private void criarPastas() {
		
		try {
			Files.createDirectories(local);
			this.localTemporario = getDefault().getPath(this.local.toString(),"temp");
			Files.createDirectories(this.localTemporario);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Pasta default " + this.local.toAbsolutePath());
				logger.debug("Pasta temporária " + this.localTemporario.toAbsolutePath());
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar fotos");
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String nomeArquivo = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if(logger.isDebugEnabled()) {
			logger.debug("Novo nome do arquivo " + nomeArquivo);
		}
		
		return nomeArquivo;
	}
	
}
