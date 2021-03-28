package test.daten;

import daten.Satz;
import daten.Uebung;
import daten.Uebung.Valid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UebungTest {

    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testGetValid() {

        List<String> existingUebungNameList = new ArrayList<>();
        Uebung uebung = new Uebung();

        assertEquals(Valid.NONAME, uebung.getValid(existingUebungNameList));

        uebung.setName("");
        assertEquals(Valid.NONAME, uebung.getValid(existingUebungNameList));

        uebung.setName("name");
        assertEquals(Valid.MASSE, uebung.getValid(existingUebungNameList));

        Satz satz = new Satz();
        uebung.getMasse().add(satz);
        assertEquals(Valid.DEFI, uebung.getValid(existingUebungNameList));

        uebung.getDefi().add(satz);
        assertEquals(Valid.VALID, uebung.getValid(existingUebungNameList));

        existingUebungNameList.add("name");
        assertEquals(Valid.NAME, uebung.getValid(existingUebungNameList));

    }
}
