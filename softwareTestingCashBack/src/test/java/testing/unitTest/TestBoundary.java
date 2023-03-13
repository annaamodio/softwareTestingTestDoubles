package testing.unitTest;

import boundary.MainFrame;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class TestBoundary {

    private static MainFrame mainFrame;

    public static void main(String args[]) throws IOException,
            AWTException, InterruptedException{

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



    }
}
