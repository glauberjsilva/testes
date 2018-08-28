package br.com.glauber.servicos;

import static br.com.glauber.matchers.MatchersProprios.caiNumaSegundaFeira;
import static br.com.glauber.matchers.MatchersProprios.ehHoje;
import static br.com.glauber.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
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

public class LocacaoTest3_UsandoMatchers {

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

	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {

		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // Isto é muito util quando um teste depende de um recurso e este pode nao está disponivel
		
		// Cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Guerra nas estrelas", 1, 10.00d));

		// Ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificação Utilizando @Role  -> public ErrorCollector error = new ErrorCollector() 
		error.checkThat(locacao.getValor(), is(10.00));
		error.checkThat(locacao.getDataLocacao(), ehHoje()); 
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1)); 
		// Mesa validação anterior sem matches proprios
		// substituido pelo Matchers Proprios heHoje() error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true)); 
		// substituido pelo Matchers Proprios ehHojeComDiferencaDias(1)  error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
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

	// Usando Matches Proprios
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY)); // Isto é muito util quando um teste depende de um recurso e este pode nao está disponivel
		
		// Cenário
		Usuario usuario = new Usuario("Usuario 1");

		List<Filme> filmes = Arrays.asList(	new Filme("Filme 1", 1, 10.00d) );
		
		// Ação
		Locacao retorno = this.service.alugarFilme(usuario, filmes);
		
		// Verificação com Matches Proprios
		//Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		Assert.assertThat(retorno.getDataRetorno(),  caiNumaSegundaFeira());
	}	

}
