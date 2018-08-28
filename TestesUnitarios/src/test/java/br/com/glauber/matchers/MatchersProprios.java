package br.com.glauber.matchers;

import java.util.Calendar;

public class MatchersProprios {
	
	public static DataDiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DataDiaSemanaMatcher(diaSemana);
	}
	
	public static DataDiaSemanaMatcher caiNumaSegundaFeira() {
		return new DataDiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0);
	}
	
	public static DataDiferencaDiasMatcher ehHojeComDiferencaDias(Integer quantidadeDias ) {
		return new DataDiferencaDiasMatcher(quantidadeDias);
	}
}
