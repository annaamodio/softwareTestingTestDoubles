package testing.unitTest;

import database.*;
import entity.ApplicazioneCashback;
import entity.Iscrizione;
import entity.ProgrammaCashback;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEntity {

    private static ApplicazioneCashback appCash;

    @Mocked
    private DBProgrammaCashback dbProgCash;

    @Mocked
    private DBIscrizione dbIscrizione;

    @Injectable DBAcquisto acq1;
    @Injectable DBAcquisto acq2;
    @Injectable DBCartaDiCredito dbCarta;

    @Injectable DBCittadino dbCittadino;

    @Test
    public void testRichiediRimborso() throws Exception{

        appCash = ApplicazioneCashback.getInstance();

        new Expectations(){{

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dbProgCash.getDataInizio(); result = new Date(cal.getTimeInMillis()); //vedi

            cal.set(Calendar.YEAR, 2022);
            cal.set(Calendar.MONTH, Calendar.JUNE);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            dbProgCash.getDataFine(); result = new Date(cal.getTimeInMillis()) ;//vedi

            dbProgCash.getMinAcquisti(); result = 1;
            dbProgCash.getMaxTetto(); result = 10;
            dbProgCash.getPercRimborso(); result = 0.2;
            dbProgCash.getIdProgramma(); result = 162022;

            dbIscrizione.getIdCittadino(); result = "ABCDEFGHI123456"; minTimes = 0; //potrebbe non essere chiamato
            dbIscrizione.getIban(); result = "IT47X3608105138230364830372";
            dbIscrizione.getPassword(); result = "qwerty7890";
            dbIscrizione.getRimborsoRicevuto(); result = 0;
            dbIscrizione.getCarteRegistrate(); result = new ArrayList<DBCartaDiCredito>(Arrays.asList(dbCarta));
            dbIscrizione.getCittadino(); result = dbCittadino;
            dbIscrizione.getProgramma(); result = dbProgCash;

            ArrayList<DBAcquisto> acqList= new ArrayList<DBAcquisto>(Arrays.asList(acq1,acq2));
            dbIscrizione.getAcquistiRegistrati(); result = acqList;

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

        assertEquals(10, appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, null));

    }

}
