package br.com.glauber.servicos;

import br.com.glauber.entidades.Usuario;

public interface SPCService {
	
	public boolean possuiNegativacao(Usuario usuario) throws Exception;
}
