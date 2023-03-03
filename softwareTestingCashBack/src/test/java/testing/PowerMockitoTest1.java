package testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalMatchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ApplicazioneCashback.class)
public class PowerMockitoTest1 {

	private static ProgrammaCashback progrCash;
	private static ApplicazioneCashback applCash;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		progrCash = mock(ProgrammaCashback.class);
		when(progrCash.creaRimborso(anyString(), anyString())).thenThrow(new IllegalArgumentException());
		when(progrCash.creaRimborso(eq("ABCDEFGHI123456"), eq("qwerty7890"))).thenReturn((float) 2.0);
		
		applCash = PowerMockito.spy(new ApplicazioneCashback());
		//PowerMockito.doNothing().when(applCash, "ricercaProgramma",162022, progrCash); --in mockito inutile
		PowerMockito.doThrow(new IllegalArgumentException()).when(applCash, "ricercaProgramma", not(eq(162022)) , any());
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1() throws Exception {
		//verifica che funzioni correttamente
		assertEquals(applCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, progrCash), 2.00, 0.1);
	}
	
	@Test
	public void test2() throws Exception {
		//eccezione per password sbagliata
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI123456", "qwer!y7890", 162022, progrCash));
	}
	
	@Test
	public void test3() throws Exception {
		//eccezione per cittadino sbagliat0
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI1%3456", "qwerty7890", 162022, progrCash));
	}
	
	@Test
	public void test4() throws Exception {
		//eccezione per programma
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI123456", "qwer!y7890", 150, progrCash));
	}
}
