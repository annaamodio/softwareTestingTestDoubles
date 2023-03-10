package testing.unitTest;

import control.CGestioneCashback;
import entity.ApplicazioneCashback;
import mockit.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestControl {
    @Mocked private ApplicazioneCashback appCash;// stattic non funziona
    @Tested static CGestioneCashback gestioneCashback;

    @Test
    public void test1()throws Exception {
        new Expectations() {{
            ApplicazioneCashback.getInstance(); result = appCash;
            appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, withNull());result = (float) 2.0;
            appCash.richiediRimborso(withNotEqual("ABCDEFGHI123456"), anyString, anyInt, withNull());result=new IllegalArgumentException();

        }};
        assertEquals(gestioneCashback.gestisciRichiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022), (float) 2.0);
        assertThrows(IllegalArgumentException.class,()->gestioneCashback.gestisciRichiediRimborso("ABCDEFGHI1@3456", "qwerty!890", 162022));
        new Verifications(){{
            ApplicazioneCashback.getInstance(); times = 2;
            appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, null);
            times = 1;
            appCash.richiediRimborso(withNotEqual("ABCDEFGHI123456"), anyString, anyInt, withNull());
        }};
    }
}
