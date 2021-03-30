package test;

import daten.EintragCount;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals(eintragCount2, eintragCount1);
        assertEquals(eintragCount4, eintragCount1);
        assertNotEquals(eintragCount3, eintragCount1);
        assertNotEquals(eintragCount1, "bla");

    }
}
