package br.com.glauber.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.com.glauber.utils.DataUtils;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer quantidadeDias;

	public DataDiferencaDiasMatcher(Integer quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
	}

	@Override
	public void describeTo(Description description) {
		Date dataEsperada = DataUtils.obterDataComDiferencaDias(quantidadeDias);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		description.appendText(format.format(dataEsperada));
	}

	@Override
	protected boolean matchesSafely(Date data) {
		// TODO Auto-generated method stub
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(quantidadeDias));
	}

}
