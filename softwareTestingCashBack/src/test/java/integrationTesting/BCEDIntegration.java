package integrationTesting;

import boundary.MainFrame;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class BCEDIntegration {

    //Integrazione di tutti i livelli

    private static MainFrame mainFrame;

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
    public void test() throws Exception {
        eseguiTest();
    }
}
