package test;

import domain.Programm;
import domain.Programm.ProgrammValid;
import domain.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgrammTest {

    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testGetValid() {
        Programm programm = new Programm();

        assertEquals(ProgrammValid.NONAME, programm.getProgrammValid());

        programm.setName("");
        assertEquals(ProgrammValid.NONAME, programm.getProgrammValid());

        programm.setName("name");
        assertEquals(ProgrammValid.TAGE, programm.getProgrammValid());

        Tag tag = new Tag();
        programm.getTage().add(tag);
        assertEquals(ProgrammValid.VALID, programm.getProgrammValid());
    }
}
