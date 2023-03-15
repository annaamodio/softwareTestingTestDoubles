package testRichiediRimborso;

import entity.ApplicazioneCashback;
import entity.ProgrammaCashback;
import mockit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


public class JMockitTest1 {

    private static ApplicazioneCashback applCash;
    @Mocked ProgrammaCashback progrCash;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception{
        applCash = ApplicazioneCashback.getInstance();
        assumeTrue(applCash!=null);
    }

    @Test
    public void test1() throws Exception{

        //risultato corretto
        new Expectations(){{
            progrCash.creaRimborso("ABCDEFGHI123456", "qwerty7890"); result=2.0;
        }};

        assertEquals(applCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, progrCash), 2.0);
    }

    @Test
    public void test2() throws Exception{

        //eccezione
        new Expectations(){{
            progrCash.creaRimborso(anyString, anyString); result = new IllegalArgumentException();
        }};
        assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso("ABCDEFGHI1%3456", "qwerty7890", 162022, progrCash));

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/pwc.csv", numLinesToSkip = 1)
    void test(String idCittadino, String password, int programma,Boolean esito) throws Exception{

        //test parametrizzato con JUnit 4: realizza la copertura pair-wise
        //il campo 'esito' è stato inserito per poter svolgere un test assertion-based

        new Expectations(){{
            progrCash.creaRimborso(anyString, anyString); result = new Delegate(){
                float delegate(String idCittadino, String password){
                    if(idCittadino.matches("ABCDEFGHI123456") && password.matches("qwerty7890")){
                        return (float) 2.0;
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            };
            minTimes=0;

        }};

        if(esito) {
            float result = applCash.richiediRimborso(idCittadino, password, programma , progrCash);
            assertEquals(result,2.0);
        }else {
            assertThrows(IllegalArgumentException.class, ()->applCash.richiediRimborso(idCittadino, password, programma , progrCash));
        }


    }


}
