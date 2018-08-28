

import org.junit.Assert;
import org.junit.Test;

import br.com.glauber.entidades.Usuario;;

public class AssertTest {

	@Test
	public void test(){
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals("Erro de comparacao", 1, 1); // A string é aparesentada quando a um erro na validação
		Assert.assertEquals(0.51234, 0.512, 0.001);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		//Para float e double
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		//Comparando strings
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = u2;
		Usuario u4 = null;
		
		//Compara igualdade de objetos utilizando o equals e hashcode da classe usuário
		Assert.assertEquals(u1, u2); 
		
		//Compara se é a mesma instancia da classe
		Assert.assertSame(u2, u2);
		Assert.assertSame(u2, u3);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertNull(u4);
		Assert.assertNotNull(u2);
	}
}
