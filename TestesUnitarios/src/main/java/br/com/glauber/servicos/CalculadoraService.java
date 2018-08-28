package br.com.glauber.servicos;

import br.com.glauber.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraService {

	public int somar(int a, int b) {
		System.out.println("Imprimindo somar");
		return a + b;
	}

	public int subtracao(int a, int b) {
		return a - b;
	}

	public int divisao(int a, int b) throws NaoPodeDividirPorZeroException {
		if (b == 0)
			throw new NaoPodeDividirPorZeroException();

		return a / b;
	}
	
	public int divide(String a, String b) {
		return Integer.valueOf(a) / Integer.valueOf(b);
	}
	
	public void imprime() {
		System.out.println("Passei aqui no método imprime()");
	}
}
