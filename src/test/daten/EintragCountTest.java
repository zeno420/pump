package test.daten;

import daten.EintragCount;
import daten.Satz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EintragCountTest {


    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testEquals() {

        EintragCount eintragCount1 = new EintragCount("name", 1);
        EintragCount eintragCount2 = new EintragCount("name", 1);
        EintragCount eintragCount3 = new EintragCount("noname", 1);
        EintragCount eintragCount4 = eintragCount1;

        assertTrue(eintragCount1.equals(eintragCount2));
        assertTrue(eintragCount1.equals(eintragCount4));
        assertFalse(eintragCount1.equals(eintragCount3));
        assertFalse(eintragCount1.equals("bal"));

    }
}
