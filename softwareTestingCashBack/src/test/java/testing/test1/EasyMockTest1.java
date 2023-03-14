package testing.test1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import static org.easymock.EasyMock.*; //per quasi tutti i test è necessario importare solo questa classe

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;


class EasyMockTest1 {

	private static ApplicazioneCashback applCash;
	private static ProgrammaCashback progrCash;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//otteniamo un'istanza di ApplicazioneCashback
		applCash = ApplicazioneCashback.getInstance();
		assumeTrue(applCash!=null);

		//creiamo il mock e definiamo il comportamento
		//creazione del mock
		progrCash = mock(ProgrammaCashback.class);
		//fase di record
		expect(progrCash.creaRimborso("ABCDEFGHI123456", "qwerty7890")).andStubReturn((float) 2.0);
		expect(progrCash.creaRimborso(anyString(), anyString())).andStubThrow(new IllegalArgumentException());
		//avvio fase di replay
		replay(progrCash);
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		applCash = null;
		assertNull(applCash);
	}

	@Test
	void test1() throws Exception{

		//test con valori corretti
		float result = applCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, progrCash);
		assertEquals(result,2.0);
		
	}
	
	@Test
	void test2() throws Exception{

		//test con lancio dell'eccezione
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI123446", "34", 162022,progrCash));
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("AAAAAAAAAAAA", "29", 162022,progrCash));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "/pwc.csv", numLinesToSkip = 1)
	void test3(String idCittadino, String password, int programma,Boolean esito) throws Exception{

		//test parametrizzato: realizza la copertura pair-wise
		//il campo 'esito' è stato inserito per poter svolgere un test assertion-based
		if(esito) {
			float result = applCash.richiediRimborso(idCittadino, password, programma , progrCash);
			assertEquals(result,2.0);
		}else {
			assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso(idCittadino, password, programma , progrCash));
		}
		
	}
	
}
