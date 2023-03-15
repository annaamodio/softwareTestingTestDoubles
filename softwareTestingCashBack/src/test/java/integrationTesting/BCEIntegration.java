package integrationTesting;

import boundary.MainFrame;
import database.*;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class BCEIntegration {

    //Integrazione dei livelli Boundary e Control con il livello Entity

    private static MainFrame mainFrame;

    @Mocked
    private DBProgrammaCashback dbProgCash; //mocking di classe

    @Mocked
    private DBIscrizione dbIscrizione; //mocking di classe

    //sono dichiarate come Injectable perchè c'è bisogno di istanze diverse
    @Injectable
    DBAcquisto acq1;
    @Injectable
    DBAcquisto acq2;
    @Injectable
    DBCartaDiCredito dbCarta;
    @Injectable
    DBCittadino dbCittadino;

    private void eseguiTest() throws Exception{
        mainFrame.main(null);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Create an instance of Robot class
        Robot robot = new Robot();

        //open MainFrame
        robot.mouseMove(500,350);
        Thread.sleep(2000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        Thread.sleep(1000);

        //inserisci programma
        robot.keyPress(KeyEvent.VK_1);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_6);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_2);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_0);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_2);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_2);
        Thread.sleep(500);

        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_SHIFT);
        //inserisci idCittadino
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_A);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_B);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_C);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_F);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_G);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_H);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_I);
        Thread.sleep(500);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_1);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_2);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_3);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_4);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_5);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_6);
        Thread.sleep(500);

        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(500);
        //inserisci password
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_Q);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_W);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_R);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_T);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_Y);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_7);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_8);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_9);
        Thread.sleep(500);
        robot.keyPress(KeyEvent.VK_0);
        Thread.sleep(1000);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        Thread.sleep(5000);
    }

    @Test
    public void test() throws Exception{

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

        eseguiTest();

    }

}
