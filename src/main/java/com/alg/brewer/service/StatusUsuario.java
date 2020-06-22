package com.alg.brewer.service;

import com.alg.brewer.repositories.UsuariosRepository;

public enum StatusUsuario {
 
	ATIVAR {
		@Override
		public void executar(Long[] codigos, UsuariosRepository repository) {
			repository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(true));
		}
	}, 
	DESATIVAR {
		@Override
		public void executar(Long[] codigos, UsuariosRepository repository) {
			repository.findByCodigoIn(codigos).forEach(u -> u.setAtivo(false));
		}
	};
	
	public abstract void executar(Long [] codigos, UsuariosRepository repository);
}
