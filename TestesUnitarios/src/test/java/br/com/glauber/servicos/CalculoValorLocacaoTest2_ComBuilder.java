package br.com.glauber.servicos;

import static br.com.glauber.builder.FilmeBuilder.umFilme;
import static br.com.glauber.builder.UsuarioBuilder.umUsuario;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.com.glauber.dao.LocacaoDAO;
import br.com.glauber.entidades.Filme;
import br.com.glauber.entidades.Locacao;
import br.com.glauber.entidades.Usuario;
import br.com.glauber.exceptions.FilmeSemEstoqueException;
import br.com.glauber.exceptions.LocadoraException;

/**
 * Usando Parameterize, Padrão de Projeto DataBuilder e Chaining Method.
 * 
 * Parameterize é uma tecnica que permite gerar conjunto de dados de forma dinamica, reduzindo o código dúplicado e/ou repetição de dados
 * Chaining Method que permite realizar o encadeamento de métodos. 
 * 
 * @author Glauber
 *
 */
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest2_ComBuilder {

	private LocacaoService service;
	private SPCService SPCService;

	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String cenario;

	@Rule
	public ErrorCollector error = new ErrorCollector();


	@Before
	public void setup() {
		this.service = new LocacaoService();
		
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class);
		this.service.setLocacaoDAO(dao);
		
		this.SPCService = Mockito.mock(SPCService.class);
		this.service.setSPCService(SPCService);
	}

	private static Filme filme1 = umFilme().agora();
	private static Filme filme2 = umFilme().agora();
	private static Filme filme3 = umFilme().agora();
	private static Filme filme4 = umFilme().agora();
	private static Filme filme5 = umFilme().agora();
	private static Filme filme6 = umFilme().agora();
	private static Filme filme7 = umFilme().agora();

	@Parameters(name = "Teste {index} = {2} --- Preço da Locação = {1}") // Imprime na console do JUnit
	public static Collection<Object[]> getParametros() {
		
		//Dados de entrada e saida respctivamente Filme e Valor da Locação
		return Arrays.asList(new Object[][] { 
			{ Arrays.asList(filme1, filme2), 8d, "2 Filmes: Não tem desconto" },
			{ Arrays.asList(filme1, filme2, filme3), 11d, "3 Filmes: 25%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4), 13d, "4 Filmes: 50%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14d, "5 Filmes: 75%" }, 
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14d, "6 Filmes: 100%" },
			{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18d, "7 Filmes: Não tem desconto" } 
		});
	}

	@Test
	public void deveraCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// Cenário
		Usuario usuario = umUsuario().agora();

		// Ação
		Locacao locacao = this.service.alugarFilme(usuario, filmes);

		this.filmes = null;
		// Verificação
		Assert.assertEquals(valorLocacao, locacao.getValor(), 0.01);
	}

}
