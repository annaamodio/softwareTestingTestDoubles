package testing.unitTest;

import control.CGestioneCashback;
import entity.ApplicazioneCashback;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestControl {
    private static ApplicazioneCashback appCash;
    @Tested static CGestioneCashback gestioneCashback;

    @Test
    public void test1()throws Exception {
        appCash=ApplicazioneCashback.getInstance(); // classe statica abbiamo fatto un mocking parziale.
        new Expectations(appCash) {{
            appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, withNull());result = (float) 2.0;
            appCash.richiediRimborso(withNotEqual("ABCDEFGHI123456"), anyString, anyInt, withNull());result=new IllegalArgumentException();

        }};
        assertEquals(gestioneCashback.gestisciRichiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022), (float) 2.0);
        assertThrows(IllegalArgumentException.class,()->gestioneCashback.gestisciRichiediRimborso("ABCDEFGHI1@3456", "qwerty!890", 162022));
        new Verifications(){{
            appCash.richiediRimborso("ABCDEFGHI123456", "qwerty7890", 162022, null);
            times = 1;
        }};
    }
}
