package br.com.glauber.dao;

import java.util.List;

import br.com.glauber.entidades.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);

	public List<Locacao> ObterLocacoesPendentes();
}