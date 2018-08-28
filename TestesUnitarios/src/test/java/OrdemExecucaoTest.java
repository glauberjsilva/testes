

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * 
 * @author Glauber
 *
 * Não é recomendo pois fere o principio FIRST
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemExecucaoTest {
	
	public static int contador = 0;
	
	@Test
	public void inica() {
		contador = 1;
	}
	
	@Test
	public void verifica() {
		Assert.assertEquals(1, contador);
	}
}
