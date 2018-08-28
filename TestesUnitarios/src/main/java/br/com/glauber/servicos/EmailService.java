package br.com.glauber.servicos;

import br.com.glauber.entidades.Usuario;

public interface EmailService {

	public void notificarAtraso(Usuario usuario);

}
