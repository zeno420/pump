package test;

import domain.Day;
import domain.Day.Valid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest {

    @Test
    public void testGetValid() {

        Day day = new Day();
        assertEquals(Valid.NAME, day.getValid());

        day.setName("");
        assertEquals(Valid.NAME, day.getValid());

        day.setName("name");
        assertEquals(Valid.VALID, day.getValid());

    }
}
