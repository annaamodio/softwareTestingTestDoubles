package testRichiediRimborso;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;

class MockitoTest1 {
	private static ApplicazioneCashback appCash;
	private static ProgrammaCashback progCash;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//otteniamo un'istanza di ApplicazioneCashback
		appCash = ApplicazioneCashback.getInstance();		
		assertNotNull(appCash);

		//creazione del mock
		progCash = mock(ProgrammaCashback.class);
		//definizione del comportamento
		when(progCash.creaRimborso(anyString(), anyString())).thenThrow(new IllegalArgumentException());
		when(progCash.creaRimborso(eq("ABCDEFGHI123456"), eq("qwerty7890"))).thenReturn((float) 10.0);
		//la seconda definizione prevale sulla prima
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		ApplicazioneCashback appCash = null;		
		assertNull(appCash);
	}

	@ParameterizedTest
	@CsvFileSource(resources ="/pwc.csv", numLinesToSkip = 1)
	void test1(String idCittadino, String password, int programma, boolean esito) throws Exception  {

		//test parametrizzato: realizza la copertura pair-wise
		//il campo 'esito' è stato inserito per poter svolgere un test assertion-based

		if(esito) {
			float result = appCash.richiediRimborso(idCittadino, password, programma, progCash);
			assertEquals(result, 10.0);
			verify(progCash).creaRimborso("ABCDEFGHI123456", "qwerty7890");
		}else {
			assertThrows(IllegalArgumentException.class, () -> appCash.richiediRimborso(idCittadino, password, programma, progCash));
		}
	}

}
