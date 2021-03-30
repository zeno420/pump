package test;

import daten.Satz;
import daten.Satz.Valid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SatzTest {

    @BeforeAll
    static void initAll() throws Exception {
    }

    @Test
    public void testGetValid() {

        Satz satz = new Satz();

        assertEquals(Valid.WIEDERHOLUNGEN, satz.getValid());

        satz.setWiederholungen("1");
        assertEquals(Valid.GEWICHT, satz.getValid());

        satz.setGewicht("1");
        assertEquals(Valid.VALID, satz.getValid());

    }
}
