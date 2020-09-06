package com.alg.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alg.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("local")
@Component
public class FotoStorageLocal implements FotoStorage {
	
	private static final String THUMBNAIL_PREFIX = "thumbnail.";
	private static Logger logger = LoggerFactory.getLogger(FotoStorageLocal.class);
	private Path local;
	
	public FotoStorageLocal() {
		this.local = getDefault().getPath(System.getProperty("user.home"), ".brewerfotos");
		criarPastas();
	}
	
	public FotoStorageLocal(Path local) {
		this.local = local;
		this.criarPastas();
	}
	
	@Override
	public byte[] recuperarThumbnail(String nome) {
		return recuperarFoto(THUMBNAIL_PREFIX + nome);
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
	public String salvar(MultipartFile[] file) {
		
		String novoNome = null;
		
		if(file !=null && file.length > 0) {
			MultipartFile files = file[0];
			try {
				novoNome = renomearArquivo(files.getOriginalFilename());
				files.transferTo(new File(this.local.toAbsolutePath() + getDefault().getSeparator() + novoNome));
			} catch (Exception e) {
				throw new RuntimeException("erro salvando a foto " + e.getMessage());
			}
		}
		
		try {
			Thumbnails.of(this.local.resolve(novoNome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail.");
		}
		
		return novoNome;
	}
	
	@Override
	public String getUrl(String foto) {
		return "http://localhost:8080/brewer/fotos/" + foto;
	}
	
	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
		} catch (IOException e) {
			logger.warn(String.format("Erro apagando foto %s. Mensagem %s.", foto, e.getMessage()));
		}
	}

	private void criarPastas() {
		
		try {
			Files.createDirectories(local);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Pasta default " + this.local.toAbsolutePath());
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar fotos");
		}
		
	}
}
