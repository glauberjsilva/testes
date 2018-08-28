
package br.com.glauber.servicos;

import static br.com.glauber.matchers.MatchersProprios.ehHoje;
import static br.com.glauber.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.glauber.builder.LocacaoBuilder;
import br.com.glauber.dao.LocacaoDAO;
import br.com.glauber.entidades.Locacao;


public class LocacaoTest5_MockitoArgumentCaptor {
	
	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private SPCService SPCService;
	@Mock
	private EmailService emailService;
	@Mock
	private LocacaoDAO dao;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveProrrogarUmaLocacao() {
		// Cenario
		Locacao locacao = LocacaoBuilder.umaLocacao().agora();
		
		// Ação
		service.prorrogarLocacao(locacao, 3); 
		
		// Verificação
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();

		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
	}

}
