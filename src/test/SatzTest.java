package test;

import domain.Satz;
import domain.Satz.Valid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SatzTest {

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
