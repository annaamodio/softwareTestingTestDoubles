package testing;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;

class MockitoTest1 {
	private static ApplicazioneCashback appCash;
	private static ProgrammaCashback progCash;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		appCash = ApplicazioneCashback.getInstance();		
		assertNotNull(appCash);
		
		progCash = mock(ProgrammaCashback.class);

		when(progCash.creaRimborso(anyString(), anyString())).thenThrow(new IllegalArgumentException());
		when(progCash.creaRimborso(eq("ABCDEFGHI123456"), eq("qwerty7890"))).thenReturn((float) 2.0);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		ApplicazioneCashback appCash = null;		
		assertNull(appCash);
	}

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@ParameterizedTest
	@CsvFileSource(resources ="pwc.csv")
	void test1(String idCittadino, String password, int programma, boolean esito) throws Exception  {
			
		if(esito) {
			float result = appCash.richiediRimborso(idCittadino, password, programma, progCash);
			assertEquals(result, 2.0);	
			verify(progCash).creaRimborso("ABCDEFGHI123456", "qwerty7890");
		}else {
			assertThrows(IllegalArgumentException.class, () -> appCash.richiediRimborso(idCittadino, password, programma, progCash));
		}
	}
	
	

	@AfterEach
	void tearDown() throws Exception {
	}



}
