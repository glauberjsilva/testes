package br.com.glauber.servicos;

import static br.com.glauber.utils.DataUtils.isMesmaData;
import static br.com.glauber.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.com.glauber.dao.LocacaoDAO;
import br.com.glauber.entidades.Filme;
import br.com.glauber.entidades.Locacao;
import br.com.glauber.entidades.Usuario;
import br.com.glauber.exceptions.FilmeSemEstoqueException;
import br.com.glauber.exceptions.LocadoraException;
import br.com.glauber.utils.DataUtils;

public class LocacaoTest2_UsandoIgnore {

	private LocacaoService service;
	private SPCService SPCService;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();


	@Before
	public void setup() {
		this.service = new LocacaoService();
		
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		this.service.setLocacaoDAO(dao);
		
		this.SPCService = Mockito.mock(SPCService.class);
		this.service.setSPCService(SPCService);
	}
	@After
	public void tearDown() {
	}

	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		//Assume.assumeTrue Isto é muito util quando um teste depende de um recurso e este pode nao está disponivel
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); 
		
		// Cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Guerra nas estrelas", 1, 10.00d));

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificação JUnit
		Assert.assertEquals(10.00d, locacao.getValor(), 0.01);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

		// Verificação utilizando Fluente Interface através da API hamcrest (Matchers)
		// Usando import estatico para CoreMatchers
		// Verifique quê
		assertThat(locacao.getValor(), is(10.00)); // CoreMatchers.is();
		assertThat(locacao.getValor(), is(equalTo(10.00)));
		assertThat(locacao.getValor(), is(not(5.00)));
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

		// Utilizando Role
		// indicado quando o teste tem o mesmo cenário e ações
		error.checkThat(locacao.getValor(), is(10.00)); // CoreMatchers.is();
		error.checkThat(locacao.getValor(), is(10.00)); // CoreMatchers.is();
		error.checkThat(locacao.getValor(), is(equalTo(10.00)));
		error.checkThat(locacao.getValor(), is(not(5.00)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 0, 4.0));

		// acao
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 1, 4.0));

		try {
			// acao
			service.alugarFilme(null, filmes);
			Assert.fail(); // força parar o teste caso não seja lançado nenhuma exceção esperada
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuário vazio"));
		}

		// System.out.println("Somente esta forma de teste consegue seguir com o teste
		// após lançar a execeção");
	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");

		// Exception deve ficar antes da ação (chamada do método alugarFilmes)
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		// acao
		this.service.alugarFilme(usuario, null);
	}

	@Test
	// ( @Ignore Este deverá ser usado apenas quando um teste estiver inacabado ou impossivel de ser realizado)
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		// Assume.assumeTrue Isto é muito util quando um teste depende de um recurso e este pode nao está disponivel
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); 
		
		// Cenário
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(	new Filme("Filme 1", 1, 10.00d) );
		
		// Ação
		Locacao retorno = this.service.alugarFilme(usuario, filmes);
		
		// Verificação
		boolean ehSegunda =  DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda); // O teste falha mas não da erro quando o dia da semana não for sabado.
	}	

}
