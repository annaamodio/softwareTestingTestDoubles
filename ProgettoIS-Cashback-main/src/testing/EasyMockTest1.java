package testing;

import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;


class EasyMockTest1 {
	
	
	//commit di prova
	
	private static ApplicazioneCashback applCash;
	private static ProgrammaCashback progrCash;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		applCash = ApplicazioneCashback.getInstance();
		assumeNotNull(applCash);
		progrCash = mock(ProgrammaCashback.class);
		expect(progrCash.creaRimborso("ABCDEFGHI123456", "qwerty7890")).andStubReturn((float) 2.0);
		expect(progrCash.creaRimborso(not(eq("ABCDEFGHI123456")), anyString())).andStubThrow(new IllegalArgumentException());
		expect(progrCash.creaRimborso(anyString(), not(eq("qwerty7890")))).andStubThrow(new IllegalArgumentException());
		replay(progrCash);
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		applCash = null;
		assertNull(applCash);
	}

	@Test
	void test1() throws Exception{
		
		float result = applCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, progrCash);
		assertEquals(result,2.0);
		
	}
	
	@Test
	void test2() throws Exception{
		
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI123446", "34", 162022,progrCash));
		assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("AAAAAAAAAAAA", "29", 162022,progrCash));
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "pwc.csv")
	void test3(String idCittadino, String password, int programma,Boolean esito) throws Exception{
		
		if(esito) {
			float result = applCash.richiediRimborso(idCittadino, password, programma , progrCash);
			assertEquals(result,2.0);
		}else {
			assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso(idCittadino, password, programma , progrCash));
		}
		
	}
	
}
