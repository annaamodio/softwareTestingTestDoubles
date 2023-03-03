package testing;

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.not;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.easymock.EasyMock.*;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ApplicazioneCashback.class)
public class PowerEasyMockTest1 {

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

}
