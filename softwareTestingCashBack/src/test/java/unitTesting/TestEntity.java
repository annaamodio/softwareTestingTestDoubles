package unitTesting;

import database.*;
import entity.ApplicazioneCashback;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntity {

    private static ApplicazioneCashback appCash;

    @Mocked
    DBProgrammaCashback dbProgCash; //mocking di classe

    @Mocked
    DBIscrizione dbIscrizione; //mocking di classe

    //sono dichiarate come Injectable perchè c'è bisogno di istanze diverse
    @Injectable DBAcquisto acq1;
    @Injectable DBAcquisto acq2;
    @Injectable DBCartaDiCredito dbCarta;
    @Injectable DBCittadino dbCittadino;

    @Test
    public void testRichiediRimborso() throws Exception{

        appCash = ApplicazioneCashback.getInstance(); //istanziata normalmente

        new Expectations(){{

            //DEFINIZIONE DEL COMPORTAMENTO DI DBProgrammaCashback

            //il programma usava il tipo DATE il cui costruttore è deprecato
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dbProgCash.getDataInizio(); result = new Date(cal.getTimeInMillis());

            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.JUNE);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dbProgCash.getDataFine(); result = new Date(cal.getTimeInMillis()) ;

            dbProgCash.getMinAcquisti(); result = 1;
            dbProgCash.getMaxTetto(); result = 10;
            dbProgCash.getPercRimborso(); result = 0.2;
            dbProgCash.getIdProgramma(); result = 162022;


            //DEFINIZIONE DEL COMPORTAMENTO DI DBIscrizione

            dbIscrizione.getIdCittadino(); result = "ABCDEFGH123456"; minTimes = 0; //potrebbe non essere chiamato
            dbIscrizione.getIban(); result = "IT47X3608105138230364830372";
            dbIscrizione.getPassword(); result = "qwerty7890";
            dbIscrizione.getRimborsoRicevuto(); result = 0;
            dbIscrizione.getCarteRegistrate(); result = new ArrayList<DBCartaDiCredito>(Arrays.asList(dbCarta));
            dbIscrizione.getCittadino(); result = dbCittadino;
            dbIscrizione.getProgramma(); result = dbProgCash;
            //Il metodo restituisce un arrayList di oggetti mock creati per l'occorrenza
            dbIscrizione.getAcquistiRegistrati(); result = new ArrayList<DBAcquisto>(Arrays.asList(acq1,acq2));

            //DEFINIZIONE DEL COMPORTAMENTO DI DBAcquisto

            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 17);
            acq1.getData(); result = new Date(cal.getTimeInMillis());
            acq1.getImporto(); result = 393.30;

            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            acq2.getData(); result =new Date(cal.getTimeInMillis());
            acq2.getImporto(); result = 780.30;


        }};

        //il rimborso che si ottiene è 10, pari al tetto massimo.
        assertEquals(10, appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, null));

    }

}
