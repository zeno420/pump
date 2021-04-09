package test;

import domain.Day;
import domain.Program;
import domain.Program.ProgramValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramTest {

    @Test
    public void testGetValid() {
        Program program = new Program();

        assertEquals(ProgramValid.NONAME, program.getProgramValid());

        program.setName("");
        assertEquals(ProgramValid.NONAME, program.getProgramValid());

        program.setName("name");
        assertEquals(ProgramValid.DAYS, program.getProgramValid());

        Day day = new Day();
        program.getDays().add(day);
        assertEquals(ProgramValid.VALID, program.getProgramValid());
    }
}
