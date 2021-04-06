package test;

import domain.Satz;
import domain.Uebung;
import domain.Uebung.UebungValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UebungTest {

    @Test
    public void testGetValid() {

        Uebung uebung = new Uebung();

        assertEquals(UebungValid.NONAME, uebung.getUebungValid());

        uebung.setName("");
        assertEquals(UebungValid.NONAME, uebung.getUebungValid());

        uebung.setName("name");
        assertEquals(UebungValid.MASSE, uebung.getUebungValid());

        Satz satz = new Satz();
        uebung.getMasse().add(satz);
        assertEquals(UebungValid.DEFI, uebung.getUebungValid());

        uebung.getDefi().add(satz);
        assertEquals(UebungValid.VALID, uebung.getUebungValid());

    }
}
