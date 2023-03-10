package testing.test1;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;
import junitparams.JUnitParamsRunner;
//import junitparams.Parameters;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runners.Parameterized.Parameters;
import junitparams.*;
import junitparams.converters.Param;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(ApplicazioneCashback.class)
public class PowerMockitoTest1 {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{"ABCDEFGHI123456","qwerty7890",162022,true},
				{"ABCDEFGHI123456","qwerty7890",150,false},
				{"ABCDEFGHI123456","101112",11001122,false},
				{"ABCDEFGHI123456","111111111111111111",-125125,false},
				{"ABCDEFGHI123456","qwer!y&890",162022,false},
				{"a1b2b3","101112",-125125,false},
				{"a1b2b3","111111111111111111",162022,false},
				{"a1b2b3","qwer!y&890",150,false},
				{"bbbbbbbbbbbbbbbbbb","qwerty7890",-125125,false},
				{"bbbbbbbbbbbbbbbbbb","101112",162022,false},
				{"bbbbbbbbbbbbbbbbbb","111111111111111111",150,false},
				{"bbbbbbbbbbbbbbbbbb","qwer!y&890",11001122,false},
				{"ABCDEFGHI12@45%","qwerty7890",162022,false},
				{"ABCDEFGHI12@45%","101112",150,false},
				{"ABCDEFGHI12@45%","111111111111111111",11001122,false},
				{"ABCDEFGHI12@45%","qwer!y&890",-125125,false}
		});
	}

	private String idCittadino;
	private String password;
	private int programma;
	private boolean esito;
	public PowerMockitoTest1(String idCittadino, String password, int programma, boolean esito){
		this.idCittadino = idCittadino;
		this.password = password;
		this.programma = programma;
		this.esito = esito;
	}
	private static ProgrammaCashback progrCash;
	private static ApplicazioneCashback applCash;




	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		progrCash = mock(ProgrammaCashback.class);
		when(progrCash.creaRimborso(anyString(), anyString())).thenThrow(new IllegalArgumentException());
		when(progrCash.creaRimborso(eq("ABCDEFGHI123456"), eq("qwerty7890"))).thenReturn((float) 2.0);
		
		applCash = PowerMockito.spy(ApplicazioneCashback.getInstance());
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


	@Test
	public void test5() throws Exception{

		if(esito) {
			float result = applCash.richiediRimborso(idCittadino, password, programma, progrCash);
			Assertions.assertEquals(result, 2.0);
		}else {
			assertThrows(IllegalArgumentException.class, () -> applCash.richiediRimborso(idCittadino, password, programma, progrCash));
		}
	}
}