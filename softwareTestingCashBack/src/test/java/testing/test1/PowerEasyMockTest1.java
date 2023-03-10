package testing.test1;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.not;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.easymock.EasyMock.*;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.Arrays;
import java.util.Collection;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Parameterized.class)
@PrepareForTest(ApplicazioneCashback.class)
public class PowerEasyMockTest1 {
	@Parameterized.Parameters
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
	public PowerEasyMockTest1(String idCittadino, String password, int programma, boolean esito){
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
		expect(progrCash.creaRimborso("ABCDEFGHI123456", "qwerty7890")).andStubReturn((float) 2.0);
		expect(progrCash.creaRimborso(not(eq("ABCDEFGHI123456")), anyString())).andStubThrow(new IllegalArgumentException());
		expect(progrCash.creaRimborso(anyString(), not(eq("qwerty7890")))).andStubThrow(new IllegalArgumentException());
		PowerMock.replay(progrCash);
		
		applCash = PowerMock.createPartialMock(ApplicazioneCashback.class, "ricercaProgramma");
		PowerMock.expectPrivate(applCash, "ricercaProgramma", 162022, progrCash).asStub();
		PowerMock.expectPrivate(applCash, "ricercaProgramma", not(eq(162022)), eq(progrCash)).andStubThrow(new IllegalArgumentException());
		PowerMock.replay(applCash);
		
		
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
