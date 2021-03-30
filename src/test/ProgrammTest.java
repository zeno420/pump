package test;

import daten.Programm;
import daten.Programm.Valid;
import daten.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgrammTest {

    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testGetValid() {

        List<String> existingProgrammNameList = new ArrayList<>();
        Programm programm = new Programm();

        assertEquals(Valid.NONAME, programm.getValid(existingProgrammNameList));

        programm.setName("");
        assertEquals(Valid.NONAME, programm.getValid(existingProgrammNameList));

        programm.setName("name");
        assertEquals(Valid.TAGE, programm.getValid(existingProgrammNameList));

        Tag tag = new Tag();
        programm.getTage().add(tag);
        assertEquals(Valid.VALID, programm.getValid(existingProgrammNameList));

        existingProgrammNameList.add("name");
        assertEquals(Valid.NAME, programm.getValid(existingProgrammNameList));
    }
}
